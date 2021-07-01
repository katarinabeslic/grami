let listOfSongs = [];
let listOfGenres = [];
let listOfRoles = getListOfRoles();

function validation_alert(element, message, alertType) {
    element.append(
        '<div id="alertdiv" class="alert ' + alertType + '">' +
        '<a class="close" data-dismiss="alert" aria-label="close" >×</a>' +
        '<span>' + message + '</span>' +
        '</div>');
}

function songToTable() {
    let songName = $("input[name = 'songName']").val();
    let songDuration = $("input[name = 'songDuration']").val();
    let songObj = { "songName": songName, "duration": songDuration };
    if (songName === '' || songDuration === '') {
        $('#alertdiv').remove();
        let songNotEmpty = $('#song-not-empty').val();
        validation_alert($('#song-response'), songNotEmpty, 'alert-danger');
        return;
    } else if (songDuration !== '') {
        $('#alertdiv').remove();
        let regex = /^[0-9]{1}:[0-5]{1}[0-9]{1}$/;
        if (regex.test(songDuration) === false) {
            let songDurationInvalid = $('#song-duration-invalid').val();
            validation_alert($('#song-response'), songDurationInvalid, 'alert-danger');
            return;
        }
    }
    listOfSongs.push(songObj);
    $('#songTable tbody').append(`<tr> <td>${songName}</td> <td>${songDuration}</td> 
					<td><i class="fa fa-trash-o" onclick="deleteSong(this)" style="font-size:20px"/></td> </tr>`);
    $("input[name= 'songName']").val("");
    $("input[name= 'songDuration']").val("");
}

function deleteSong(el) {
    let row = el.parentNode.parentNode;
    let tdSongName = row.getElementsByTagName("td")[0].innerHTML;
    let tdDuration = row.getElementsByTagName("td")[1].innerHTML;
    let songObj = {
        "songName": tdSongName,
        "duration": tdDuration
    };
    for (let i = listOfSongs.length; i >= 0; --i) {
        if (JSON.stringify(listOfSongs[i]) === JSON.stringify(songObj)) {
            listOfSongs.splice(i, 1);
        }
    }
    el.parentNode.parentNode.parentNode.removeChild(el.parentNode.parentNode);
}

function findOrCreateArtist() {
    let artistName = $('#artistName').val();
    $.ajax({
        type: "POST",
        url: "/admin/find-create-artist",
        data: {
            "artistName": artistName
        },
        success: function (response) {
            if (response === "saved") {
                let artistSaved = $('#artist-saved').val();
                validation_alert($('#artist-response'), artistSaved, 'alert-success');
            } else {
                let artistExists = $('#artist-exists').val();
                validation_alert($('#artist-response'), artistExists, 'alert-info');
            }
        }
    });
}

function createRecordLabel() {
    let rlName = $('#rl-name').val();
    let rlYear = $('#rl-year').val();

    // 1900 -2099
    let regEx = /^(19|20)\d{2}$/;

    if (rlName === '' || rlYear === '') {
        let rlEmpty = $('#rl-empty').val();
        validation_alert($('#rl-response'), rlEmpty, 'alert-danger');
        return;
    } else if (regEx.test(rlYear) === false) {
        $('#alertdiv').remove();
        let rlYearInvalid = $('#rl-year-invalid').val();
        validation_alert($('#rl-response'), rlYearInvalid, 'alert-danger');
        return;
    } else {
        $('#alertdiv').remove();
    }

    let rlObj = {
        "name": rlName,
        "year": rlYear
    };
    $.ajax({
        type: "POST",
        url: "/admin/create-record-label",
        contentType: 'application/json',
        data: JSON.stringify(rlObj),
        success: function (data) {
            if (data !== '') {
                $('#alertdiv').remove();
                let rlSaved = $('#rl-saved').val();
                validation_alert($('#rl-response'), rlSaved, 'alert-success');
                $('#rl-select').empty();
                for (let i = 0; i < data.length; i++) {
                    let rl = data[i];
                    $('#rl-select').append("<option>" + rl.name + ", " + rl.year + ".</option>")
                }
                $('#rl-name').val('');
                $('#rl-year').val('');
            } else {
                let rlExists = $('#rl-exists').val();
                validation_alert($('#rl-response'), rlExists, 'alert-warning');
            }
        }
    });
}

function createNewGenre() {
    let genreName = $('#genreName').val();

    if (genreName === '') {
        let genreEmpty = $('#genre-empty').val();
        validation_alert($('#genre-response'), genreEmpty, 'alert-danger');
        return;
    } else {
        $('#alertdiv').remove();
    }

    $.ajax({
        type: "POST",
        url: "/admin/create-genre",
        data: { "genreName": genreName },
        success: function (data) {
            if (data !== '') {
                $('#alertdiv').remove();
                let genreCreated = $('#genre-created').val();
                validation_alert($('#genre-response'), genreCreated, 'alert-success');
                $('#genre-select').empty();
                for (let i = 0; i < data.length; i++) {
                    let genre = data[i];
                    $('#genre-select').append('<option>' + genre.name + '</option>');
                }
                $('#genreName').val('');
            } else {
                validation_alert($('#genre-response'), "The genre already exists in the database!", 'alert-danger');
            }
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
    $.ajax({
        type: "POST",
        url: "/admin/save-vinyl",
        enctype: 'multipart/form-data',
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        success: function (response) {
            if (response === "success") {
                $('#alertdiv').remove();
                let vinylSaved = $('#vinyl-saved').val();
                validation_alert($('#vinyl-response'), vinylSaved, 'alert-success');
                clear_vinyl_form();
            } else if (response === "vinylExists") {
                $('#alertdiv').remove();
                let vinylExists = $('#vinyl-exists').val();
                validation_alert($('#vinyl-response'), vinylExists, 'alert-danger');
            } else {
                $('#alertdiv').remove();
                let vinylNotSaved = $('#vinyl-not-saved').val();
                validation_alert($('#vinyl-response'), vinylNotSaved, 'alert-danger');
            }
        }
    });
}


function updateVinyl(href) {
    let stageName = $('#artistName').val();
    let rlResult = $('.rl-chosen').val().split(',');
    let year = rlResult[1].split('.');
    let rlObj = {
        "name": rlResult[0].trim(),
        "year": year[0].trim()
    };
    let format = $('.format-chosen').val();
    listOfGenres = JSON.parse(JSON.stringify(getListOfGenres()));
    listOfSongs = JSON.parse(JSON.stringify(getListOfSongs()));
    let id = "";
    for (let i = 0; i < href.length; i++) {
        if ($.isNumeric(href[i])) {
            id = id + href[i];
        }
    }
    let imgUrl = '';
    let form = $('#fileUploadForm')[0];
    let data = new FormData(form);
    if ($('#imgUrl').get(0).files.length === 0) {
        imgUrl = $('.existingImg img').attr('src');
    } else {
        imgUrl = $('#imgUrl').val();
    }
    let formatId = $('#selected-format').find(':selected').val();
    let editedVinylJSON = {
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

    let valid = final_validation($('#name').val(), $('#description').val(), $('#price').val(), $('#stock').val(),
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
            if (response === "success") {
                $('#alertdiv').remove();
                let vinulUpdated = $('#vinyl-updated').val();
                validation_alert($('#vinyl-response'), vinulUpdated, 'alert-success');
            } else {
                $('#alertdiv').remove();
                let vinulNotUpdated = $('#vinyl-not-updated').val();
                validation_alert($('#vinyl-response'), vinulNotUpdated, 'alert-danger');
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
    let songs = [];
    $('#songTable tr').each(function () {
        let cols = [];
        $(this).find('td').each(function (colIndex, c) {
            if (c.textContent !== '') {
                let el = c.textContent;
                cols.push(el);
            }
        });
        songs.push(cols);
    });

    let songsJSON = [];
    for (let i = 1; i < songs.length; i++) {
        let songName = songs[i][0];
        let duration = songs[i][1];
        let id = songs[i][2];
        let song = {
            "id": id,
            "songName": songName,
            "duration": duration
        };
        songsJSON.push(song);
    }

    return songsJSON;
}

function getListOfGenres() {
    listOfGenres = [];
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

    listOfSongs = [];
    listOfGenres = [];
}

/* ======================= VALIDATIONS ======================== */


$("#name").focusout(function () {
    $('#alertdiv').remove();
    if ($('#name').val() === '') {
        validation_alert($('#vinyl-name-alert'), "The vinyl name can't be empty!", 'alert-danger');
    }
});

$("#description").focusout(function () {
    $('#alertdiv').remove();
    if ($('#description').val() === '') {
        validation_alert($('#description-alert'), "The description can't be empty!", 'alert-danger');
    }
});

$("#price").focusout(function () {
    $('#alertdiv').remove();
    if ($('#price').val() === '') {
        validation_alert($('#price-alert'), "Price can't be empty!", 'alert-danger');
    } else if (!$.isNumeric($('#price').val())) {
        validation_alert($('#price-alert'), "Price has to be a number!", 'alert-danger');
    }
});

$("#stock").focusout(function () {
    $('#alertdiv').remove();
    if ($('#stock').val() === '') {
        validation_alert($('#quantity-alert'), "Stock can't be empty!", 'alert-danger');
    }
});

$("#artistName").focusout(function () {
    $('#alertdiv').remove();
    if ($('#artistName').val() === '') {
        validation_alert($('#artist-response'), "Artist can't be empty!", 'alert-danger');
    }
});

function final_validation(name, description, price, stock, stageName, fileCheck, listOfSongs, listOfGenres) {
    let valid = true;
    if (name === '') {
        let vinylNameEmpty = $('#vinyl-name-empty').val();
        validation_alert($('#vinyl-name-alert'), vinylNameEmpty, 'alert-danger');
        valid = false;
    }
    if (description === '') {
        let descriptionEmpty = $('#description-empty').val();
        validation_alert($('#description-alert'), descriptionEmpty, 'alert-danger');
        $('#description-alert').show();
        valid = false;
    }
    if (!fileCheck) {
        let pictureEmpty = $('#picture-empty').val();
        validation_alert($('#image-alert'), pictureEmpty, 'alert-danger');
        valid = false;
    }
    if (price === '') {
        let priceEmpty = $('#price-empty').val();
        validation_alert($('#price-alert'), priceEmpty, 'alert-danger');
        valid = false;
    }
    if (price !== '' && !$.isNumeric($('#price').val())) {
        let priceNumber = $('#price-not-number').val();
        validation_alert($('#price-alert'), priceNumber, 'alert-danger');
        valid = false;
    }
    if (stock === '') {
        let stockEmpty = $('#stock-empty').val();
        validation_alert($('#quantity-alert'), stockEmpty, 'alert-danger');
        valid = false;
    }
    if (stageName === '') {
        let artistEmpty = $('#artist-empty').val();
        validation_alert($('#artist-response'), artistEmpty, 'alert-danger');
        valid = false;
    }
    if (listOfSongs.length === 0) {
        let tracklistEmpty = $('#tracklist-empty').val();
        validation_alert($('#song-response'), tracklistEmpty, 'alert-danger');
        valid = false;
    }
    if (listOfGenres.length === 0) {
        let genresEmpty = $('#genres-empty').val();
        validation_alert($('#genre-contains'), genresEmpty, 'alert-danger');
        valid = false;
    }
    return valid;
}

// ========================== DIALOGS =========================== //

$('.deleteVinyl').on('click', function (event) {
    event.preventDefault();
    let href = $(this).attr('href');
    console.log(href);
    let deleteVinyl = $('#delete-vinyl-question').val();
    confirmDialogDelete(deleteVinyl, href, 'http://localhost:8080/admin/catalogue');
});

$('#update-vinyl').click(
    function () {
        event.preventDefault();
        let saveChanges = $('#save-changes').val();
        confirmDialogSaveChanges(saveChanges);
    }
);

$('#cancel-change').click(
    function () {
        event.preventDefault();
        let cancel = $('#cancelOrNo').val();
        confirmDialogCancel(cancel);
    }
);

function confirmDialogCancel(message) {
    let justChecking = $('#just-checking').val();
    $('<div id="confirm-dialog"></div>').appendTo('body')
        .html('<div><h5>' + message + '</h5></div>')
        .dialog({
            modal: true,
            title: justChecking,
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
            close: function () {
                $(this).remove();
            }
        });
}

function confirmDialogDelete(message, $href, addressToGoTo) {
    let justChecking = $('#just-checking').val();
    $('<div id="confirm-dialog"></div>').appendTo('body')
        .html('<div><h5>' + message + '</h5></div>')
        .dialog({
            modal: true,
            title: justChecking,
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
                            if (data == 'success')
                                $(location).attr('href', addressToGoTo);
                        }
                    });
                    $(this).dialog("close");
                },
                No: function () {
                    $(this).dialog("close");
                }
            },
            close: function () {
                $(this).remove();
            }
        });
}

function confirmDialogSaveChanges(message) {
    let justChecking = $('#just-checking').val();
    $('<div id="confirm-dialog"></div>').appendTo('body')
        .html('<div><h5>' + message + '</h5></div>')
        .dialog({
            modal: true,
            title: justChecking,
            zIndex: 10000,
            autoOpen: true,
            width: 'auto',
            resizable: false,
            buttons: {
                Yes: function () {
                    let href = $('#update-vinyl').attr('href');
                    updateVinyl(href);
                    $(this).dialog("close");
                },
                No: function () {
                    $(this).dialog("close");
                }
            },
            close: function () {
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
    let selectedRL = $('#rl-select').find(':selected').html();
    $('.rl-chosen').val(selectedRL);
});

$('.href-icon').css('cursor', 'pointer');

$('.remove-order-href').click(
    function () {
        event.preventDefault();
        let $row = $(this).closest("td");
        let $href = $row.find("a").attr('href');
        console.log($href)
        let deleteOrder = $('#remove-order').val();
        confirmDialogDeleteOrder(deleteOrder, $href);
    }
);

function confirmDialogDeleteOrder(message, $href) {
    let justChecking = $('#just-checking').val();
    $('<div id="confirm-dialog"></div>').appendTo('body')
        .html('<div><h5>' + message + '</h5></div>')
        .dialog({
            modal: true,
            title: justChecking,
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
                            if (data == 'success')
                                $(location).attr('href', 'http://localhost:8080/admin/orders');
                        }
                    });
                    $(this).dialog("close");
                },
                No: function () {
                    $(this).dialog("close");
                }
            },
            close: function () {
                $(this).remove();
            }
        });
}

$('.edit-order').click(
    function () {
        event.preventDefault();
        let $row = $(this).closest("td");
        let $href = $row.find("a").attr('href');
        $.ajax({
            type: "GET",
            url: $href,
            success: function () {
                $(location).attr('href', $href);
            }
        });
    }
);

function cancelOrderEditing() {
    $(location).attr('href', 'http://localhost:8080/admin/orders');
}

function confirmDialogDeleteUser(el, message, $href, addressToGoTo) {
    let justChecking = $('#just-checking').val();
    $('<div id="confirm-dialog"></div>').appendTo('body')
        .html('<div><h5>' + message + '</h5></div>')
        .dialog({
            modal: true,
            title: justChecking,
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
                            if (data === "success") {
                                let userDeleted = $('#user-deleted').val();
                                validation_alert($('.user-delete-response'), userDeleted, "alert-success");
                                el.parentNode.parentNode.parentNode.removeChild(el.parentNode.parentNode);
                            }
                            if (data === "fail") {
                                let userNotDeleted = $('#user-not-deleted').val();
                                validation_alert($('.user-delete-response'), userNotDeleted, "alert-danger");
                            }
                        }
                    });
                    $(this).dialog("close");
                },
                No: function () {
                    $(this).dialog("close");
                }
            },
            close: function () {
                $(this).remove();
            }
        });
}

$('.remove-user-href').click(
    function () {
        event.preventDefault();
        if (document.getElementById("alertdiv") !== null) {
            $('#alertdiv').remove();
        }
        let $row = $(this).closest("td");
        let $href = $row.find("a").attr('href');
        let deleteUser = $('#user-question').val();
        confirmDialogDeleteUser(this, deleteUser, $href, 'http://localhost:8080/admin/users');
    }
);

function getListOfRoles() {
    let roles = [];
    $('#current-roles li').each(function () {
        let role = {"name": $(this).attr('value')};
        roles.push(role);
    });
    return roles;
}

function removeRoles() {
    listOfRoles = [];
    $('#current-roles').empty();
}

$('#role-add').change(
    function () {
        if (document.getElementById("alertdiv") !== null) {
            $('#alertdiv').remove();
        }
        if (listOfRoles.length < 2) {
            let selectedRole = $('#role-add').find(':selected').val();
            if (listOfRoles.some(e => e.name === selectedRole)) {
                let userHasRole = $('#user-has-role').val();
                validation_alert($('#role-alert'), userHasRole, 'alert-danger');
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
            }
        } else {
            let noMoreRoles = $('#no-more-roles').val();
            validation_alert($('#role-alert'), noMoreRoles, 'alert-danger');
        }
    }
);

$('.update-user-btn').click(
    function () {
        event.preventDefault();
        if (listOfRoles.length === 0) {
            let noRoles = $('#no-roles').val();
            validation_alert($('#role-alert'), noRoles, 'alert-danger');
            return;
        }
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
                if (data === "success") {
                    let userUpdated = $('#user-updated').val();
                    validation_alert($('.user-edited-response'), userUpdated, "alert-success");
                } else {
                    let userNotUpdated = $('#user-not-updated').val();
                    validation_alert($('.user-edited-response'), userNotUpdated, "alert-info");
                }
            }
        });
    }
);