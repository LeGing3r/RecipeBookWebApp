$(function () {
    $('[data-close]').on('click', function () {
        $(this).parent().remove();
    });

    let addButtons = document.getElementsByClassName('addIngredientBtn');
    for (let i = 0; i < addButtons.length - 1; i++)
        addButtons[i].style.display = 'none';
});

function changeImg(event) {
    let img = document.getElementById('recipeImg');
    img.src = URL.createObjectURL(event.target.files[0]);
}

function toggleMenu() {
    let menu = document.getElementById("menu");
    menu.classList.toggle('visible');
    let icon = document.getElementById('menuButtonIcon');
    if (icon.innerHTML === 'menu') {
        icon.innerHTML = 'close';
    } else {
        icon.innerHTML = 'menu';
    }
}

function changeSelection(selector) {
    let choice = selector.options[selector.selectedIndex].value;
    document.getElementsByName('query')[0].placeholder = 'Search for a ' + choice;
}

function showCategories() {
    let inputBox = document.getElementById('catFilter');
    let text = inputBox.value.toLocaleLowerCase();
    let list = document.getElementsByClassName('name');
    for (let item of list) {
        let catName = item.innerHTML.toLocaleLowerCase();
        if (!catName.match(text))
            item.parentElement.parentElement.parentElement.parentElement.parentElement.style.display = 'none';
        else
            item.parentElement.parentElement.parentElement.parentElement.parentElement.style.display = 'block';
    }
}


let prevScrollpos = window.pageYOffset;
window.onscroll = function () {
    let currentScrollPos = window.pageYOffset;
    if (prevScrollpos > currentScrollPos) {
        document.getElementById("nav").style.top = "-1px";
    } else if (window.innerWidth <= 1270) {
        document.getElementById("nav").style.top = "-500px";
    }
    prevScrollpos = currentScrollPos;
}