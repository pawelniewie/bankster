function getHistory(days) {
    $("#lastdays_radio").click();
    $("#lastdays_days").val(days);
    $("#lastdays_period").val("D");
    $("#Submit").click();
}