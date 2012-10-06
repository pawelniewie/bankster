var transactions = [];
$("#account_operations ul.table li").each(function(idx) {
                                          var row = $(this);
                                          if (!row.hasClass("header") && !row.hasClass("footer") && !row.hasClass("noDataMessage")) {
                                            var dates = $("p.Date span", row);
                                            var opDate = dates.first().text();
                                            var acDate = dates.last().text();
                                            var description = $(".OperationDescription", row).text();
                                            var values = $("p.Amount span", row);
                                            var amount = values.first().text();
                                            var balance = values.last().text();
                                            transactions.push({
                                                            "operationDate" : opDate,
                                                            "accountedDate" : acDate,
                                                            "description": description,
                                                            "amount": amount,
                                                            "balance": balance
                                                            });
                                          }
                                          
                                          
                                          });
JSON.stringify(transactions);