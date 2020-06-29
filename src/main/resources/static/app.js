  $(function(){
     $('[data-close]').on('click',function(){
             $(this).parent().remove();
         });

         var addButtons = document.getElementsByClassName('addIngredientBtn');
         for(i = 0; i < addButtons.length -1; i++)
            addButtons[i].style.display = 'none';
  });

function changeImg(event){
    var img = document.getElementById('recipeImg');
    img.src = URL.createObjectURL(event.target.files[0]);
}

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

 function changeSelection(selector){
    var choice = selector.options[selector.selectedIndex].value;
    document.getElementsByName('query')[0].placeholder = 'Search for a ' + choice;
 }

 function showCategories(){
    var inputBox = document.getElementById('catFilter');
    var text = inputBox.value.toLocaleLowerCase();
    var list = document.getElementsByClassName('name');
    for(let item of list){
        let catName = item.innerHTML.toLocaleLowerCase();
        if(!catName.match(text))
            item.parentElement.parentElement.parentElement.parentElement.parentElement.style.display = 'none';
        else
            item.parentElement.parentElement.parentElement.parentElement.parentElement.style.display = 'block';
    }
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