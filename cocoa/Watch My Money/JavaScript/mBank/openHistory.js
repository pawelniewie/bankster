function openHistory(account) {
    var li = $("ul li").filter(function(idx) {
                      return $("a", $(this)).filter(function(idx) {
                                                    return $(this).text() == account;
                                                    }).length > 0;
                      });
    var link = $("p.Actions a", li).filter(function(idx) {
                                return $(this).text() == "Historia operacji";
                                });
    link.click();
}