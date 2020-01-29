var logID = 'log',
    log = $('<div id="'+logID+'"></div>');
$('body').append(log);
$('[type*="radio"]').change(function () {
    $("#ratingcontainer").hide();
    $(this).closest("form").submit();
    $(".rating").attr('disabled', true);
    var me = $(this);
    log.html(me.attr('value'));
});
document.getElementById("rating-5").onclick = function(){
    document.getElementById("rating-1").disabled=true;
    document.getElementById("rating-2").disabled=true;
    document.getElementById("rating-3").disabled=true;
    document.getElementById("rating-4").disabled=true;
}

document.getElementById("rating-4").onclick = function(){
    document.getElementById("rating-1").disabled=true;
    document.getElementById("rating-2").disabled=true;
    document.getElementById("rating-3").disabled=true;
    document.getElementById("rating-5").disabled=true;
}

document.getElementById("rating-3").onclick = function(){
    document.getElementById("rating-1").disabled=true;
    document.getElementById("rating-2").disabled=true;
    document.getElementById("rating-5").disabled=true;
    document.getElementById("rating-4").disabled=true;
}

document.getElementById("rating-2").onclick = function(){
    document.getElementById("rating-1").disabled=true;
    document.getElementById("rating-5").disabled=true;
    document.getElementById("rating-3").disabled=true;
    document.getElementById("rating-4").disabled=true;
}

document.getElementById("rating-1").onclick = function(){
    document.getElementById("rating-5").disabled=true;
    document.getElementById("rating-2").disabled=true;
    document.getElementById("rating-3").disabled=true;
    document.getElementById("rating-4").disabled=true;
}
