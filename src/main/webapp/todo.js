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

    $.fn.selectAll = function() {
        if ($(this).get(0).setSelectionRange) {
            $(this).get(0).setSelectionRange(0, pos);
        } else if ($(this).get(0).createTextRange) {
            var range = $(this).get(0).createTextRange();
            range.collapse(true);
            range.moveEnd('character', pos);
            range.moveStart('character', 0);
            range.select();
        }
    };

    $.fn.setLastCursorPosition = function() {
        $(this).setCursorPosition($(this).val().length);
    };
})(jQuery);

function handleCaretPositions() {
    $(".nav input").selectAll();
}

$(function() {
    handleCaretPositions();
});

function doAfterAjaxHandling() {
    $(function() {
      handleCaretPositions();
    });
}
