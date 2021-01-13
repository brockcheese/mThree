$(document).ready(function () {
    $("#akronInfoDiv").hide();
    $("#minneapolisInfoDiv").hide();
    $("#louisvilleInfoDiv").hide();
    $("#mainButton").on("click", function() {
        $("#mainInfoDiv").show();
        $("#akronInfoDiv").hide();
        $("#minneapolisInfoDiv").hide();
        $("#louisvilleInfoDiv").hide();
    })
    $("#akronButton").on("click", function() {
        $("#mainInfoDiv").hide();
        $("#akronInfoDiv").show();
        $("#minneapolisInfoDiv").hide();
        $("#louisvilleInfoDiv").hide();
        $("#akronWeather").hide();
    })
    $("#minneapolisButton").on("click", function() {
        $("#mainInfoDiv").hide();
        $("#akronInfoDiv").hide();
        $("#minneapolisInfoDiv").show();
        $("#louisvilleInfoDiv").hide();
        $("#minneapolisWeather").hide();
    })
    $("#louisvilleButton").on("click", function() {
        $("#mainInfoDiv").hide();
        $("#akronInfoDiv").hide();
        $("#minneapolisInfoDiv").hide();
        $("#louisvilleInfoDiv").show();
        $("#louisvilleWeather").hide();
    })
    $(".btn-default").on("click", function() {
        $("#akronWeather").toggle();
        $("#minneapolisWeather").toggle();
        $("#louisvilleWeather").toggle();
    })
    $("td").hover(
        function () {
            $(this).parent().css("background-color", "WhiteSmoke");
        },
        function () {
            $(this).parent().css("background-color", "");
        }
    );
});