;(function($) {
    $.fn.setCursorPosition = function(pos) {
        if ($(this).get(0).setSelectionRange) {
            $(this).get(0).setSelectionRange(pos, pos);
        } else if ($(this).get(0).createTextRange) {
            var range = $(this).get(0).createTextRange();
            range.collapse(true);
            range.moveEnd('character', pos);
            range.moveStart('character', pos);
            range.select();
        }
    };

    $.fn.setLastCursorPosition = function() {
        $(this).setCursorPosition($(this).val().length);
    };
})(jQuery);

function handleCaretPositions() {
    $(".nav input").setLastCursorPosition();
}

$(function() {
    handleCaretPositions();
});

function doAfterAjaxHandling() {
    $(function() {
      handleCaretPositions();
    });
}
