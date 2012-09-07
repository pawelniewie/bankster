var labels = [];
$('p.Account a').each(function(idx) {
                      labels.push($(this).text());
                      });
JSON.stringify(labels);