var listOfSongs = new Array;
var listOfGenres = new Array;
var listOfRoles = new Array;

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
    let songObj = { "songName": songName, "duration": songDuration };

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
        for (let i=0; listOfSongs.length; i++) {
            let song = listOfSongs[i];
            console.log(song);
            console.log(listOfSongs)
            if (songObj == song) {
                console.log("AHA")
            }
        }
        validation_alert($('#song-response'), "The song was already added!", 'alert-danger');
        return;
    }


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
    let artistName = $('#artistName').val();
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
                validation_alert($('#genre-response'), "The genre already exists in the database!", 'alert-danger');
            }
        },
        error: function (xhr, status, error) {
            var err = eval("(" + xhr.responseText + ")");
            console.log(err.Message);
        }
    });
}

function saveNewVinyl() {
    let name = $('#name').val();
    let description = $('#description').val();
    let price = $('#price').val();
    let stock = $('#stock').val();
    let selectedFormat = $('#selected-format').find(':selected');
    let formatObj = {
        "id": selectedFormat.val(),
        "name": selectedFormat.html()
    };
    let stageName = $('#artistName').val();
    let selectedRL = $('#rl-select').find(':selected');


    let rlResult = selectedRL.html().split(',');
    let year = rlResult[1].split('.');
    let rlObj = {
        "name": rlResult[0],
        "year": year[0]
    };

    let vinylData = {
        'vinylName': name,
        'description': description,
        'price': price,
        'stock': stock,
        'artist': { stageName },
        'recordLabel': rlObj,
        'format': formatObj,
        'genres': listOfGenres,
        'songs': listOfSongs,
    };

    let form = $('#fileUploadForm')[0];
    let data = new FormData(form);
    data.append("vinylData", JSON.stringify(vinylData));

    let fileCheck = $('#imgUrl').val();

    let valid = final_validation(name, description, price, stock, stageName, fileCheck, listOfSongs, listOfGenres);
    if (!valid) {
        return;
    }

    console.log(vinylData);

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
            } else if (response == "vinylExists") {
                $('#alertdiv').remove();
                validation_alert($('#vinyl-response'), "Oh no! The vinyl with that title and artist already exists :(", 'alert-danger');
            } else {
                $('#alertdiv').remove();
                validation_alert($('#vinyl-response'), "Oh no! The vinyl wasn't saved :(", 'alert-danger');
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
        'stock': $('#stock').val(),
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

    var valid = final_validation($('#name').val(), $('#description').val(), $('#price').val(), $('#stock').val(),
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
            } else {
                $('#alertdiv').remove();
                validation_alert($('#vinyl-response'), "Oh no! The vinyl wasn't updated :(", 'alert-danger');
            }
        }
    });
}

function addToListOfGenres() {
    if (document.getElementById("alertdiv") !== null) {
        $('#alertdiv').remove();
    }
    let selectedGenre = $('#genre-select').find(':selected');
    if (getListOfGenres().some(e => e.name === selectedGenre.html())) {
        validation_alert($('#genre-contains'), "The selected genre has already been added!", 'alert-danger');
    } else {
        let genreObj = {
            "name": selectedGenre.html()
        };
        listOfGenres.push(genreObj);
        $("#selected-genres ul").append(`<li>${selectedGenre.html()}</li>`);
    }
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
    listOfGenres = new Array;
    $('#genres-ul li').each(function (index, li) {
        let genre = { "name": li.textContent };
        listOfGenres.push(genre);
    });
    return listOfGenres;
}

function clear_vinyl_form() {
    $('#name').val('');
    $('#description').val('');
    $('#imgUrl').val('');
    $('#price').val('');
    $('#stock').val('');
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

$("#stock").focusout(function () {
    $('#alertdiv').remove();
    if ($('#stock').val() == '') {
        validation_alert($('#quantity-alert'), "Stock can't be empty!", 'alert-danger');
    }
});

$("#artistName").focusout(function () {
    $('#alertdiv').remove();
    if ($('#artistName').val() == '') {
        validation_alert($('#artist-response'), "Artist can't be empty!", 'alert-danger');
    }
});

function final_validation(name, description, price, stock, stageName, fileCheck, listOfSongs, listOfGenres) {
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
    if (stock == '') {
        validation_alert($('#quantity-alert'), "Stock can't be empty!", 'alert-danger');
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
        validation_alert($('#genre-contains'), "Vinyl has to have at least one genre!", 'alert-danger');
        valid = false;
    }
    return valid;
}

// ========================== DIALOGS =========================== //

$('.deleteVinyl').on('click', function (event) {
    event.preventDefault();
    var href = $(this).attr('href');
    console.log(href);
    confirmDialogDelete('Are you sure you want to delete this vinyl?', href, 'http://localhost:8080/admin/catalogue');
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

function confirmDialogDelete(message, $href, addressToGoTo) {
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
                        url: $href,
                        success: function (data) {
                            $(location).attr('href', addressToGoTo);
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
    if (document.getElementById("alertdiv") !== null) {
        $('#alertdiv').remove();
    }
    $('#genres-ul').empty();
    listOfGenres = [];
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

$('.p-show').change(
    function () {
        let selected = $(this).val();
        $('#itemsPerPage').val(selected);
        $('#filter-form').submit();
    }
);

$(function () {
    $('#light-pagination').pagination({
        items: $('#totalItems').val(),
        itemsOnPage: $('#itemsPerPage').val(),
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
});

$('.href-icon').css('cursor', 'pointer');

$('.remove-order').click(
    function () {
        event.preventDefault();
        let $row = $(this).closest("td");
        let $href = $row.find("a").attr('href');
        console.log($href);
        confirmDialogDelete("Are you sure you want to remove the order?", $href, 'http://localhost:8080/admin/orders');
    }
);

$('.edit-order').click(
    function () {
        event.preventDefault();
        let $row = $(this).closest("td");
        let $href = $row.find("a").attr('href');
        $.ajax({
            type: "GET",
            url: $href,
            success: function (data) {
                $(location).attr('href', $href);
            }
        });
    }
);

function cancelOrderEditing() {
    $(location).attr('href', 'http://localhost:8080/admin/orders');
}

function confirmDialogDeleteUser(el, message, $href, addressToGoTo) {
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
                        url: $href,
                        success: function (data) {
                            if (data == "success") {
                                validation_alert($('.user-delete-response'), "The selected user was successfully deleted!", "alert-success");
                                el.parentNode.parentNode.parentNode.removeChild(el.parentNode.parentNode);
                            }
                            if (data == "fail") {
                                validation_alert($('.user-delete-response'), "The selected user made orders in the Grami store so he can't be deleted!", "alert-danger");
                            }
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

$('.remove-user').click(
    function () {
        if (document.getElementById("alertdiv") !== null) {
            $('#alertdiv').remove();
        }
        event.preventDefault();
        let $row = $(this).closest("td");
        let $href = $row.find("a").attr('href');
        confirmDialogDeleteUser(this, "Are you sure you want to remove the user?", $href, 'http://localhost:8080/admin/users');
    }
);

function getListOfRoles() {
    listOfRoles = new Array;
    $('#current-roles li').each(function (index, li) {
        let role = {"name": $(this).attr('value')};
        listOfRoles.push(role);
    });
    return listOfRoles;
}

function removeRoles() {
    console.log("ahaaa");
    $('#current-roles').empty();
}

$('#role-add').change(
    function () {
        if (document.getElementById("alertdiv") !== null) {
            $('#alertdiv').remove();
        }
        if (getListOfRoles().length < 2) {
            let selectedRole = $('#role-add').find(':selected').val();
            if (getListOfRoles().some(e => e.name === selectedRole)) {
                validation_alert($('#role-alert'), "The user already has the selected role!", 'alert-danger');
                return;
            } else {
                let roleObj = {"name": selectedRole};
                listOfRoles.push(roleObj);
                let classToAppend = '';
                if (selectedRole === 'ROLE_CUSTOMER') {
                    classToAppend = 'badge-success';
                } else {
                    classToAppend = 'badge-danger';
                }
                $("#role-part ul").append('<li style="padding-top: 10px"><span style="padding: 10px" class="badge ' + classToAppend + '">' + selectedRole + '</span></li>');
                console.log(listOfRoles)
            }
        } else {
            validation_alert($('#role-alert'), "There's no more roles to add!", 'alert-danger');
            return;
        }
    }
);

$('.update-user-btn').click(
    function () {
        event.preventDefault();
        let user = {
            "id": $('#user-id').val(),
            "userRoles": listOfRoles,
        };
        $.ajax({
            type: "POST",
            url: "/admin/edit-user",
            data: JSON.stringify(user),
            contentType: 'application/json',
            success: function (data) {
                if (document.getElementById("alertdiv") !== null) {
                    $('#alertdiv').remove();
                }
                if (data == "success") {
                    validation_alert($('.user-edited-response'), "The user was successfully updated!", "alert-success");
                } else {
                    validation_alert($('.user-edited-response'), "No changes were made!", "alert-info");
                }
            }
        });
    }
);