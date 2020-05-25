 function toggleMenu(){
    var menu = document.getElementById("menu");
    menu.classList.toggle('visible');
    var icon = document.getElementById('menuButtonIcon');
    if(icon.innerHTML === 'menu'){
        icon.innerHTML = 'close';
    }else{
        icon.innerHTML = 'menu';
    }
 }

 function strikeThrough(i){
    var x = 'items' + i;
    var name = document.getElementById(x + '.name');
    var qty = document.getElementById(x + '.qty');
    name.classList.toggle("strkthrgh");
    qty.classList.toggle('strkthrgh');
 }

 var prevScrollpos = window.pageYOffset;
 window.onscroll = function() {
 var currentScrollPos = window.pageYOffset;
   if (prevScrollpos > currentScrollPos) {
     document.getElementById("nav").style.top = "-1px";
   } else if(window.innerWidth <=  1270) {
     document.getElementById("nav").style.top = "-500px";
   }
   prevScrollpos = currentScrollPos;
 }