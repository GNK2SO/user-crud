jQuery.validator.addMethod('not_blank', function (value, element) {
    return value.replaceAll(" ", "").length > 0;
}, 'This field must not be blank');

jQuery.validator.addMethod('not_whitespace', function (value, element) {
    return value.search(/\s/g) < 0;
}, 'This field must not have white space');

jQuery.validator.addMethod('only_alphanumeric_whitespace', function (value, element) {
    return value.match(/^[\w\s]+$/g) != null;
}, 'This field must have only alphanumeric or white space');

jQuery.validator.addMethod('only_digit', function (value, element) {
    return value.match(/^[\d]+$/g) != null;
}, 'This field must have only digits');