$('#selected-format').on('change', function() {
    var selectedFormat = $('#selected-format').find(':selected').html();
    $('.format-chosen').val(selectedFormat);
})

$('.the-format').on('click', function(event) {
    event.preventDefault();
    var value = $(this).attr('value');
    console.log(value);
    $('#format-selected').val(value);
    $('#filter-form').submit();
});

$('.sorting').on('change', function() {
    var selected = $(this).val();
    $('#input-sort').val(selected);
    $('#filter-form').submit();
});

$(function() {
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
        onPageClick: function(pageNumber) {
            $('#page').val(pageNumber)
            $('#filter-form').submit()
        }
    });
});

/*
$('#update-user').on('click', function() {
	$('#form-user').submit();
});*/

function checkPasswordMatch() {
    var password = $("#new-password").val();
    var confirmPassword = $("#confirm-password").val();
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

$('#update-user').click(function() {
    if ($('#new-password').val() == '') {
        $('#form-user').submit()
    } else {
        $('#passwordModal').modal('show');
    }
});

/*
function parsePrice(price) {
    var parsed = price.split(" ");
    return parsed[0];
}

$('.qty-dec').click(function() {
    var price = parsePrice($('.itemPrice').html());
    var totalPrice = parsePrice($('.totalPrice').html());
    var quantity = $('.itemQuantity').val() - 1;

    if (quantity >= 0) {
        totalPrice = price * quantity;
        $('.totalPrice').val(totalPrice);
        $('.totalPrice').html(totalPrice + ' RSD');
    }
});

$('.qty-inc').click(function() {
    var price = parsePrice($('.itemPrice').html());
    var totalPrice = parsePrice($('.totalPrice').html());
    var quantity = +$('.itemQuantity').val() + 1;

    totalPrice = price * quantity;
    $('.totalPrice').val(totalPrice);
    $('.totalPrice').html(totalPrice + ' RSD');
});
*/

$('#place-order').click(function() {
    event.preventDefault();
    console.log("prevented");
    var paypal = $('#pc-paypal').is(":checked");

    var orderObject = {
        "price": $('#price').val(),
        "currency": $('#currency').val(),
        "method": $('#method').val(),
        "intent": $('#intent').val(),
        "description": $('#description').val()
    };

    console.log(orderObject);

    if(paypal) {
        $.ajax({
            type: "POST",
            url: "/paypal-pay",
            contentType: 'application/json',
            data: JSON.stringify(orderObject),
            success: function() {
                console.log("success")
            }
        });
    }
});