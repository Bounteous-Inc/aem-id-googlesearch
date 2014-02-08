jQuery(document).ready(function() {
	getResults($.urlParam('q'),1);
});

function getResults(q,currentTab) {
	console.log("Getting the results", q);
	if(typeof q !== 'undefined') {
		$.getJSON('/services/SearchServlet?q='+q+'&currentTab='+currentTab, function(data) {
			buildResultTable(data.results,data.totalPages);
			buildPagination(q,data.startPage,data.endPage,data.totalPages,data.currentTab);
		});
	}
}

$.urlParam = function(name){
    var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
    return results[1] || 0;
}

function buildResultTable(results,totalPages) {
	
	 if (totalPages > 0) {
		 var table='<table>';
		 $.each( results, function( index, item){
	         var actualItem = $.parseJSON(item);
	           /* add to html string started above*/
		 		table+="<tr><td align='left' class='gm_title'><a href='"+actualItem.link+"'>"+actualItem.htmlTitle+"</a></td></tr>";
		 		table+="<tr><td align='left' class='gm_text'>"+actualItem.htmlSnippet+"</td></tr>";
		 		table+="<tr><td align='left' class='gm_link'>"+actualItem.link+"</td></tr>";
	     });
	     table+='</table>';
	     $("#results").html( table );
     } else {
    	 $("#results").html( "<b class='search_res'>No matches found.</b>" );
     }
}

function buildPagination(q,startPage,endPage,totalPages,currentTab) {
	var html='';
	
	if (currentTab > 1) {
		html+="<a class='gm_tablink' href='#' onclick=\"getResults('"+q+"',"+(currentTab-1)+");\">&lt; Previous</a>";
	}
	
	for (var i=startPage;i<=endPage;i++) {
		if (i == currentTab) {
			html+="<a class='gm_tablink gm_tablink_num active_page' href='#' onclick='return false;'>"+i+"</a>";
		} else {
			html+="<a class='gm_tablink gm_tablink_num' href='#' onclick=\"getResults('"+q+"',"+i+");\">"+i+"</a>";
		}
	}
	
	if (currentTab < totalPages) {
		html+="<a class='gm_tablink' href='#' onclick=\"getResults('"+q+"',"+(currentTab+1)+");\">Next &gt;</a>";
	}
	
	$("#paginator").html( html );
}

function runSearch(event, q) {
    if (event.which == 13 || event.keyCode == 13) {
    	getResults(q.value,1);
        return false;
    }
    return true;
}