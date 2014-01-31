
jQuery(document).ready(function() {
	$('.search_res, .paginator').css('visibility','hidden');
	$("#results").html('<img class="serch_loader" src="/etc/designs/ringcentral/images/demo/loader_yellow.gif">');
	setTimeout(function(){
		getResults($.urlParam('q'),1);
		getResultsKb($.urlParam('q'),1);
		$('.search_res, .paginator').css('visibility','visible');
	},500);
});

function getResults(q,currentTab) {
	var number = parseInt(q);
	if(typeof q !== 'undefined' && q !='') {
		$.getJSON('/services/SearchServlet?q='+q+'&currentTab='+currentTab, function(data) {
			buildResultTable(data.results,data.totalPages,number);
			buildPagination(q,data.startPage,data.endPage,data.totalPages,data.currentTab);
			startNextOM(data.totalPages);
		});
	}
}

function getResultsKb(q,currentTab) {
	if(typeof q !== 'undefined' && q !='') {
		$.getJSON('/services/KBSearchServlet?q='+q+'&currentTab='+currentTab, function(data) {
			buildResultTableKB(data.results,data.totalPages);
		});
	}
}
$.urlParam = function(name){
    var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
    return results[1] || 0;
}

function buildResultTable(results,totalPages,number) {
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
		 $(".search_res").html("Search Results");
     } else {
		if(number > 8000000000 && number < 20000000000) {
			$(".search_res").html('<b>Search Results</b><span> Are you looking for phone number? <br><a href="/ordernow/choose-your-toll-free-number.html?numdef=800&tfsf=1">Select the number you want.</a></span>');
			$("#results").html("");
		}else{
			$("#results").html("");
			$(".search_res").html("No matches found.");
		}
     }
}

function buildResultTableKB(results,totalPages) {
	 if (totalPages > 0) {
		 var table='<table>';
		 $.each( results, function( index, item){
			if(index < 5){
				var actualItem = $.parseJSON(item);
				table+="<tr><td align='left' class='kb_title'><a href='"+actualItem.link+"'>"+actualItem.htmlTitle+"</a></td></tr>";
				table+="<tr><td align='left' class='kb_text'>"+actualItem.htmlSnippet+"</td></tr>";
			}
	     });
	     table+='</table>';
	     $(".output_kb_results").html(table);
     } else {
		 $(".output_kb_results").html("");
     }
}

function buildPagination(q,startPage,endPage,totalPages,currentTab) {
	var html='';
	if (currentTab > 1) {
		html+="<a class='gm_tablink' href='#' onclick=\"getResults('"+q+"',"+(currentTab-1)+");\">&lt; Previous</a>";
	}
	if(endPage > 10) endPage = 10;
	for (var i=1;i<=endPage;i++) {
		if (i == currentTab) {
			html+="<a class='gm_tablink gm_tablink_num active_page' href='#' onclick='return false;'>"+i+"</a>";
		} else {
			html+="<a class='gm_tablink gm_tablink_num' href='#' onclick=\"getResults('"+q+"',"+i+");\">"+i+"</a>";
		}
	}
	if (currentTab < totalPages) {
		if (currentTab < 10){
			html+="<a class='gm_tablink' href='#' onclick=\"getResults('"+q+"',"+(currentTab+1)+");\">Next &gt;</a>";
		}else{
			html+="";
		}
	}
	$("#paginator").html( html );
}

function relocationPage(){
	var href = window.location.href.substring(0, window.location.href.indexOf('?'));
	var qs = window.location.href.substring(window.location.href.indexOf('?') + 1, window.location.href.length);
	var newParam = 'q=' + q.value;
	if (qs.indexOf('q=') == -1) {
		if (qs == '') {
			qs = '?'
		}
		else {
			qs = qs + '&'
		}
		qs = qs + newParam;
	}
	else {
		var start = qs.indexOf("q=");
		var end = qs.indexOf("&", start);
		if (end == -1) {
			end = qs.length;
		}
		var curParam = qs.substring(start, end);
		qs = qs.replace(curParam, newParam);
	}
	window.location.replace(href + '?' + qs);
}

function runSearch(event, q) {
    if (event.which == 13 || event.keyCode == 13) {
		relocationPage(q.value);
        return false;
   }
    return true;
}

function runClickSearch(q) {
	relocationPage(q.value);
    return true;
}
