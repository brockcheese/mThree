$(document).ready(function () {
    $("h1").css('text-align', 'center');
    $("h2").css('text-align', 'center');
    $(".myBannerHeading").addClass("page-header").removeClass("myBannerHeading");
    $("#yellowHeading").text("Yellow Team");
    $("#orangeTeamList").css('background-color', 'orange');
    $("#blueTeamList").css('background-color', 'blue');
    $("#redTeamList").css('background-color', 'red');
    $("#yellowTeamList").css('background-color', 'yellow');
    $("#yellowTeamList").append('<li>Joseph Banks</li><li>Simon Jones</li>');
    $("#oops").hide();
    $("#footerPlaceholder").remove();
    $("#footer").append('<p>Brock James Pace broqpace@gmail.com</p>').css({'font-family': 'Courier', 'font-size': '24px'});
});