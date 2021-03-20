var listOfSongs = new Array;
var listOfGenres = new Array;

function validation_alert(element, message, alerttype) {
	element.append(
		'<div id="alertdiv" class="alert ' + alerttype + '">' +
		'<a class="close" data-dismiss="alert" aria-label="close" >×</a>' +
		'<span>' + message + '</span>' +
		'</div>');
}

function songToTable() {
	let songName = $("input[name = 'songName']").val();
	let songDuration = $("input[name = 'songDuration']").val();

	if (songName == '' || songDuration == '') {
		$('#alertdiv').remove();
		validation_alert($('#song-response'), "Song name or duration can't be empty fields!", 'alert-danger');
		return;
	} else if (songDuration != '') {
		$('#alertdiv').remove();
		var regex = /^[0-9]{1}:[0-5]{1}[0-9]{1}$/;
		if (regex.test(songDuration) == false) {
			validation_alert($('#song-response'), "Invalid format for song duration!", 'alert-danger');
			return;
		}
	} else {
		$('#alertdiv').remove();
	}

	var songObj = {
		"songName": songName,
		"duration": songDuration
	};

	listOfSongs.push(songObj);

	$('#songTable tbody').append(`<tr> <td>${songName}</td> <td>${songDuration}</td> 
					<td><i class="fa fa-trash-o" onclick="deleteSong(this)" style="font-size:26px"/></td> </tr>`);
	$("input[name= 'songName']").val("");
	$("input[name= 'songDuration']").val("");
}

function deleteSong(el) {
	var row = el.parentNode.parentNode;
	var tdSongName = row.getElementsByTagName("td")[0].innerHTML;
	var tdDuration = row.getElementsByTagName("td")[1].innerHTML;

	var songObj = {
		"songName": tdSongName,
		"duration": tdDuration
	};

	for (var i = listOfSongs.length; i >= 0; --i) {
		if (JSON.stringify(listOfSongs[i]) == JSON.stringify(songObj)) {
			listOfSongs.splice(i, 1);
		}
	}

	el.parentNode.parentNode.parentNode.removeChild(el.parentNode.parentNode);
}

function findOrCreateArtist() {
	var artistName = $("input[name = 'artistName']").val();
	console.log(artistName);

	$.ajax({
		type: "POST",
		url: "/admin/find-create-artist",
		data: {
			"artistName": artistName
		},
		success: function (response) {
			if (response == "saved") {
				validation_alert($('#artist-response'), "The artist was saved to the database!", 'alert-success');
			} else {
				validation_alert($('#artist-response'), "The artist already exists in the database! ", 'alert-success');
			}
		},
		error: function (xhr, status, error) {
			var err = eval("(" + xhr.responseText + ")");
			console.log(err.Message);
		}
	});
}

function createRecordLabel() {
	var rlName = $('#rl-name').val();
	var rlYear = $('#rl-year').val();

	// 1900 -2099
	var regEx = /^(19|20)\d{2}$/;

	if (rlName == '' || rlYear == '') {
		validation_alert($('#rl-response'), "Name and year both have to be filled!", 'alert-danger');
		return;
	} else if (regEx.test(rlYear) == false) {
		$('#alertdiv').remove();
		validation_alert($('#rl-response'), "Year format not valid!", 'alert-danger');
		return;
	} else {
		$('#alertdiv').remove();
	}

	var rlObj = {
		"name": rlName,
		"year": rlYear
	};
	console.log(rlObj);

	$.ajax({
		type: "POST",
		url: "/admin/create-record-label",
		contentType: 'application/json',
		data: JSON.stringify(rlObj),
		success: function (data) {
			console.log(data);
			if (data != '') {
				$('#alertdiv').remove();
				validation_alert($('#rl-response'), "The record label was successfully saved to the database!", 'alert-success');
				console.log(data[data.length - 1]);
				$('#rl-select').empty();
				for (var i = 0; i < data.length; i++) {
					var rl = data[i];
					$('#rl-select').append("<option>" + rl.name + ", " + rl.year + ".</option>")
				}
				$('#rl-name').val('');
				$('#rl-year').val('');
			} else {
				validation_alert($('#rl-response'), "The record label already exists in the database!", 'alert-warning');
			}
		}
	});
}

function createNewGenre() {
	var genreName = $('#genreName').val();

	if (genreName == '') {
		validation_alert($('#genre-response'), "The genre name has to be filled!", 'alert-danger');
		return;
	} else {
		$('#alertdiv').remove();
	}

	$.ajax({
		type: "POST",
		url: "/admin/create-genre",
		data: {
			"genreName": genreName
		},
		success: function (data) {
			if (data != '') {
				$('#alertdiv').remove();
				validation_alert($('#genre-response'), "The new genre was successfully saved to the database!", 'alert-success');
				$('#genre-select').empty();
				for (var i = 0; i < data.length; i++) {
					var genre = data[i];
					$('#genre-select').append('<option>' + genre.name + '</option>');
				}
				$('#genreName').val('');
			} else {
				validation_alert($('#genre-response'), "The genre already exists in the database!", 'alert-warning');
			}
		},
		error: function (xhr, status, error) {
			var err = eval("(" + xhr.responseText + ")");
			console.log(err.Message);
		}
	});
}

function saveNewVinyl() {
	var name = $('#name').val();
	var description = $('#description').val();
	var price = $('#price').val();
	var quantity = $('#quantity').val();
	var selectedFormat = $('#selected-format').find(':selected');
	var formatObj = {
		"id": selectedFormat.val(),
		"name": selectedFormat.html()
	};
	var stageName = $('#artistName').val();
	var selectedRL = $('#rl-select').find(':selected');


	var rlResult = selectedRL.html().split(',');
	var year = rlResult[1].split('.');
	var rlObj = {
		"name": rlResult[0],
		"year": year[0]
	};

	var vinylData = {
		'vinylName': name,
		'description': description,
		'price': price,
		'quantity': quantity,
		'artist': {
			stageName
		},
		'recordLabel': rlObj,
		'format': formatObj,
		'genres': listOfGenres,
		'songs': listOfSongs,
	};

	var form = $('#fileUploadForm')[0];
	var data = new FormData(form);
	data.append("vinylData", JSON.stringify(vinylData));

	var fileCheck = $('#imgUrl').val();

	var valid = final_validation(name, description, price, quantity, stageName, fileCheck, listOfSongs, listOfGenres);
	if (!valid) {
		return;
	}

	$.ajax({
		type: "POST",
		url: "/admin/save-vinyl",
		enctype: 'multipart/form-data',
		data: data,
		processData: false,
		contentType: false,
		cache: false,
		success: function (response) {
			if (response == "success") {
				$('#alertdiv').remove();
				validation_alert($('#vinyl-response'), "The vinyl was successfully saved to the database!", 'alert-success');
				clear_vinyl_form();
			} else {
				$('#alertdiv').remove();
				validation_alert($('#vinyl-response'), "Oh no! The viny wasn't saved :(", 'alert-danger');
			}
		}
	});
}


function updateVinyl(href) {
	var stageName = $('#artistName').val();
	var rlResult = $('.rl-chosen').val().split(',');
	var year = rlResult[1].split('.');
	var rlObj = {
		"name": rlResult[0].trim(),
		"year": year[0].trim()
	};
	var format = $('.format-chosen').val();
	listOfGenres = JSON.parse(JSON.stringify(getListOfGenres()));
	listOfSongs = JSON.parse(JSON.stringify(getListOfSongs()));

	var id = "";

	for (var i = 0; i < href.length; i++) {
		if ($.isNumeric(href[i])) {
			id = id + href[i];
		}
	}

	var imgUrl = '';
	var form = $('#fileUploadForm')[0];
	var data = new FormData(form);
	if ($('#imgUrl').get(0).files.length === 0) {
		imgUrl = $('.existingImg img').attr('src');
	}

	var formatId = $('#selected-format').find(':selected').val();

	var editedVinylJSON = {
		'id': id,
		'vinylName': $('#name').val(),
		'description': $('#description').val(),
		'imgUrl': imgUrl,
		'price': $('#price').val(),
		'quantity': $('#quantity').val(),
		'artist': {
			stageName
		},
		'recordLabel': rlObj,
		'format': {
			"id": formatId,
			"name": format
		},
		'genres': listOfGenres,
		'songs': listOfSongs
	};

	var valid = final_validation($('#name').val(), $('#description').val(), $('#price').val(), $('#quantity').val(),
		stageName, imgUrl, listOfSongs, listOfGenres);

	if (!valid) {
		return;
	}

	data.append("editedVinylJSON", JSON.stringify(editedVinylJSON));

	$.ajax({
		type: "POST",
		url: href,
		enctype: 'multipart/form-data',
		data: data,
		processData: false,
		contentType: false,
		cache: false,
		success: function (response) {
			if (response == "success") {
				$('#alertdiv').remove();
				validation_alert($('#vinyl-response'), "The vinyl was successfully updated!", 'alert-success');
				clear_vinyl_form();
				$('#img-y').css('display', 'none');
			} else {
				$('#alertdiv').remove();
				validation_alert($('#vinyl-response'), "Oh no! The vinyl wasn't updated :(", 'alert-danger');
			}
		}
	});
}

function addToListOfGenres() {
	var selectedGenre = $('#genre-select').find(':selected');
	console.log(selectedGenre.html());

	var genreObj = {
		"name": selectedGenre.html()
	};
	listOfGenres.push(genreObj);
	$("#selected-genres ul").append(`<li>${selectedGenre.html()}</li>`);
}


function getListOfSongs() {
	var songs = [];
	$('#songTable tr').each(function (index, tr) {
		var cols = [];
		$(this).find('td').each(function (colIndex, c) {
			if (c.textContent != '') {
				var el = c.textContent;
				cols.push(el);
			}
		});
		songs.push(cols);
	});

	var songsJSON = new Array;
	for (var i = 1; i < songs.length; i++) {
		var songName = songs[i][0];
		var duration = songs[i][1];
		var id = songs[i][2];
		var song = {
			"id": id,
			"songName": songName,
			"duration": duration
		};
		songsJSON.push(song);
	}

	return songsJSON;
}

function getListOfGenres() {
	var genres = new Array;
	$('#genres-ul li').each(function (index, li) {
		genre = {
			"name": li.textContent
		};
		genres.push(genre);
	});
	return genres;
}

function clear_vinyl_form() {
	$('#name').val('');
	$('#description').val('');
	$('#imgUrl').val('');
	$('#price').val('');
	$('#quantity').val('');
	$('#selected-format').val($("#selected-format option:first").val());
	$('#artistName').val('');
	$('#songTable tbody').empty();
	$('#rl-select').val($("#rl-select option:first").val());
	$('#genres-ul li').remove();
	$('#genre-select').val($("#genre-select option:first").val());

	listOfSongs = new Array;
	listOfGenres = new Array;
}

/* ======================= VALIDATIONS ======================== */


$("#name").focusout(function () {
	$('#alertdiv').remove();
	if ($('#name').val() == '') {
		validation_alert($('#vinyl-name-alert'), "The vinyl name can't be empty!", 'alert-danger');
	}
});

$("#description").focusout(function () {
	$('#alertdiv').remove();
	if ($('#description').val() == '') {
		validation_alert($('#description-alert'), "The description can't be empty!", 'alert-danger');
	}
});

$("#price").focusout(function () {
	$('#alertdiv').remove();
	if ($('#price').val() == '') {
		validation_alert($('#price-alert'), "Price can't be empty!", 'alert-danger');
	} else if (!$.isNumeric($('#price').val())) {
		validation_alert($('#price-alert'), "Price has to be a number!", 'alert-danger');
	}
});

$("#quantity").focusout(function () {
	$('#alertdiv').remove();
	if ($('#quantity').val() == '') {
		validation_alert($('#quantity-alert'), "Quantity can't be empty!", 'alert-danger');
	} else if (!$.isNumeric($('#quantity').val())) {
		validation_alert($('#quantity-alert'), "Quantity has to be a number!", 'alert-danger');
	}
});

$("#artistName").focusout(function () {
	$('#alertdiv').remove();
	if ($('#artistName').val() == '') {
		validation_alert($('#artist-response'), "Artist can't be empty!", 'alert-danger');
	}
});

function final_validation(name, description, price, quantity, stageName, fileCheck, listOfSongs, listOfGenres) {
	var valid = true;
	if (name == '') {
		validation_alert($('#vinyl-name-alert'), "The vinyl name can't be empty!", 'alert-danger');
		valid = false;
	}
	if (description == '') {
		validation_alert($('#description-alert'), "The description can't be empty!", 'alert-danger');
		$('#description-alert').show();
		valid = false;
	}
	if (!fileCheck) {
		validation_alert($('#image-alert'), "You have to select an image for the vinyl!", 'alert-danger');
		valid = false;
	}
	if (price == '') {
		validation_alert($('#price-alert'), "Price can't be empty!", 'alert-danger');
		valid = false;
	}
	if (price != '' && !$.isNumeric($('#price').val())) {
		validation_alert($('#price-alert'), "Price has to be a number!", 'alert-danger');
		valid = false;
	}
	if (quantity == '') {
		validation_alert($('#quantity-alert'), "Quantity can't be empty!", 'alert-danger');
		valid = false;
	} else if (!$.isNumeric(quantity)) {
		validation_alert($('#quantity-alert'), "Quantity has to be a number!", 'alert-danger');
		valid = false;
	}
	if (stageName == '') {
		validation_alert($('#artist-response'), "Artist can't be empty!", 'alert-danger');
		valid = false;
	}
	if (listOfSongs.length == 0) {
		validation_alert($('#song-response'), "Tracklist can't be empty!", 'alert-danger');
		valid = false;
	}
	if (listOfGenres.length == 0) {
		validation_alert($('#genre-response'), "Vinyl has to have at least one genre!", 'alert-danger');
		valid = false;
	}
	return valid;
}

// ========================== DIALOGS =========================== //

$('.deleteVinyl').on('click', function (event) {
	event.preventDefault();
	var href = $(this).attr('href');
	console.log(href);
	confirmDialogDelete('Are you sure you want to delete this vinyl?', href);
});

function confirmDialogCancel(message) {
	$('<div id="confirm-dialog"></div>').appendTo('body')
		.html('<div><h5>' + message + '</h5></div>')
		.dialog({
			modal: true,
			title: 'Just Checking:',
			zIndex: 10000,
			autoOpen: true,
			width: 'auto',
			resizable: false,
			buttons: {
				Yes: function () {
					$(location).attr('href', 'http://localhost:8080/admin/catalogue');
					$(this).dialog("close");
				},
				No: function () {
					$(this).dialog("close");
				}
			},
			close: function (event, ui) {
				$(this).remove();
			}
		});
}

function confirmDialogDelete(message, href) {
	$('<div id="confirm-dialog"></div>').appendTo('body')
		.html('<div><h5>' + message + '</h5></div>')
		.dialog({
			modal: true,
			title: 'Just Checking:',
			zIndex: 10000,
			autoOpen: true,
			width: 'auto',
			resizable: false,
			buttons: {
				Yes: function () {
					$.ajax({
						type: "GET",
						url: href,
						success: function (data) {
							$(location).attr('href', 'http://localhost:8080/admin/catalogue');
						}
					});
					$(this).dialog("close");
				},
				No: function () {
					$(this).dialog("close");
				}
			},
			close: function (event, ui) {
				$(this).remove();
			}
		});
}

function confirmDialogSaveChanges(message) {
	$('<div id="confirm-dialog"></div>').appendTo('body')
		.html('<div><h5>' + message + '</h5></div>')
		.dialog({
			modal: true,
			title: 'Just Checking:',
			zIndex: 10000,
			autoOpen: true,
			width: 'auto',
			resizable: false,
			buttons: {
				Yes: function () {
					var href = $('#update-vinyl').attr('href');
					updateVinyl(href);
					$(this).dialog("close");
				},
				No: function () {
					$(this).dialog("close");
				}
			},
			close: function (event, ui) {
				$(this).remove();
			}
		});
}

$('.reset-genres').on('click', function () {
	$('#genres-ul').empty();
});

$('#rl-select').on('change', function () {
	var selectedRL = $('#rl-select').find(':selected').html();
	$('.rl-chosen').val(selectedRL);
});

$('#selected-format').on('change', function () {
	var selectedFormat = $('#selected-format').find(':selected').html();
	$('.format-chosen').val(selectedFormat);
})

$('.the-format').on('click', function (event) {
	event.preventDefault();
	var value = $(this).attr('value');
	console.log(value);
	$('#format-selected').val(value);
	$('#filter-form').submit();
});

$('.sorting').on('change', function () {
	var selected = $(this).val();
	$('#input-sort').val(selected);
	$('#filter-form').submit();
});

$(function () {
	$('#light-pagination').pagination({
		items: $('#totalitems').val(),
		itemsOnPage: $('#itemsperpage').val(),
		currentPage: $('#page').val(),
		cssStyle: 'light-theme',
		useAnchors: false,
		prevText: "<<",
		nextText: ">>",
		displayedPages: 1,
		edges: 1,
		onPageClick: function (pageNumber) {
			$('#page').val(pageNumber)
			$('#filter-form').submit()
		}
	});
})