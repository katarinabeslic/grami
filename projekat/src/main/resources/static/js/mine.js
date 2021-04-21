let href = window.location.pathname;
if (href == '/') {

}

$('#locales').change(function () {
    let selectedOption = $('#locales').val();
    if (selectedOption != '') {
        window.location.replace('?lang=' + selectedOption);
    }
});

$('#selected-format').on('change', function () {
    let selectedFormat = $('#selected-format').find(':selected').html();
    $('.format-chosen').val(selectedFormat);
})

$('.the-format').on('click', function (event) {
    event.preventDefault();
    let value = $(this).attr('value');
    console.log(value);
    $('#format-selected').val(value);
    $('#filter-form').submit();
});

$('.sorting').on('change', function () {
    let selected = $(this).val();
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

function checkPasswordMatch() {
    let password = $("#new-password").val();
    let confirmPassword = $("#confirm-password").val();
    if (password == "" && confirmPassword == "") {
        $("#update-user").attr("disabled", false);
    } else {
        if (password != confirmPassword) {
            $("#update-user").attr("disabled", true);
            $("#confirm-password").addClass("is-invalid");
        } else {
            $("#update-user").attr("disabled", false);
            $("#confirm-password").removeClass("is-invalid");
            $("#confirm-password").addClass("is-valid");
        }
    }
}

$("#confirm-password").keyup(checkPasswordMatch);
$("#new-password").keyup(checkPasswordMatch);

$('#update-user').click(function () {
    if ($('#new-password').val() == '') {
        $('#new-password').val("no");
        $('#form-user').submit();
    } else {
        $('#passwordModal').modal('show');
    }
});

$('#pp-btn').click(function () {
    let addressNull = $('#isAddressNull').val();
    console.log(addressNull)
    if (addressNull) {
        $('#pp-btn').attr('disabled', true);
    } else {
        $('#pp-btn').attr('disabled', false);
    }

    let orderObject = {
        "price": $('#price').val(),
        "currency": $('#currency').val(),
        "method": $('#method').val(),
        "intent" : $('#intent').val(),
        "description" : $('#description').val()
    };
    console.log(orderObject);
    $.ajax({
        type: "POST",
        url: "/paypal-pay",
        contentType: 'application/json',
        data: JSON.stringify(orderObject),
        success: function (response) {
            if (response == "fail") {
                $(location).attr('href', 'http://localhost:8080/error');
            }
            else {
                $(location).attr('href', response);
            }
        }
    });
});

let cardInfoNull = $('#isCardInfoNull').val();
if (cardInfoNull) {
    $('#place-order').attr('disabled', true);
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
                            $(location).attr('href', data);
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

$('.remove-cart-item').click(function () {
        event.preventDefault();
        let href = $('.item-href').attr('href');
        confirmDialogDelete("Are you sure you want to remove the selected item from cart?", href);
    }
);

$('.profile-btn').click(
    function () {
        $(location).attr('href', 'http://localhost:8080/my-profile');
    }
);

$('.address-btn').click(
    function () {
        $(location).attr('href', 'http://localhost:8080/my-address');
    }
);

$('.card-btn').click(
    function () {
        $(location).attr('href', 'http://localhost:8080/payment-method');
    }
);

$('.category-btn').click(
    function () {
        $(location).attr('href', 'http://localhost:8080/shop');
    }
);

$('.qty-dec').click(
    function () {
        let qty = $('.itemQuantity').val();
        if (qty > 1) {
            let newQty = qty - 1;
            let cartItemId = $('#cartItemId').val();
            let rData = {
                "cartItemId" : parseInt(cartItemId),
                "newQuantity" : newQty
            }
            $.ajax({
                type: "POST",
                url: "/decrease-cart-qty",
                contentType: 'application/json',
                data: JSON.stringify(rData),
                success: function () {
                    $(location).attr('href', 'http://localhost:8080/cart');
                }
            });
        }
    }
);

$('.qty-inc').click(
    function () {
        let qty = parseInt($('.itemQuantity').val()) + 1;
        let cartItemId = parseInt($('#cartItemId').val());
        let rData = {
            "cartItemId" : cartItemId,
            "newQuantity" : qty
        }
        $.ajax({
            type: "POST",
            url: "/increase-cart-qty",
            contentType: 'application/json',
            data: JSON.stringify(rData),
            success: function (response) {
                if (response == "success") {
                    $(location).attr('href', 'http://localhost:8080/cart');
                } else {
                    console.log("OH NOOO")
                }
            },

        });
    }
);
