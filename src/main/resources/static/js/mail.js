
function SendMail()
{
    var lineText = document.getElementById("lineText").innerText;
    var helpText = document.getElementById("helpText").innerText;
    var link = document.getElementById("link").innerText;
    window.location.href = "mailto:?body=Logfile Zeile:%0D%0A" + lineText + "%0D%0A%0AHilfestellung:%0D%0A" + helpText + "%0D%0A%0AWeiterführender Link:%0D%0A" + link + "%0D%0A%0A";
}