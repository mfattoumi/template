// here we define global variable
var ajaxdestination="";

function getdata(what,where) { // get data from source (what)
 try {
   xmlhttp = window.XMLHttpRequest?new XMLHttpRequest():
  		new ActiveXObject("Microsoft.XMLHTTP");
 }
 catch (e) { /* do nothing */ }

 document.getElementById(where).innerHTML ="<center><img src='../images/v4/loadinfo.net.gif'></center>";
// we are defining the destination DIV id, must be stored in global variable (ajaxdestination)
 ajaxdestination=where;
 xmlhttp.onreadystatechange = triggered; // when request finished, call the function to put result to destination DIV
    
 
 
 xmlhttp.open("POST", "reve.php",true);
 xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
 xmlhttp.send(what);
 
  makeMagic();

  return false;
}

function triggered() { // put data returned by requested URL to selected DIV
  if (xmlhttp.readyState == 4) if (xmlhttp.status == 200) 
    document.getElementById(ajaxdestination).innerHTML =xmlhttp.responseText;
}



