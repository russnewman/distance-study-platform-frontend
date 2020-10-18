var cards = document.querySelectorAll('.card');
cards.forEach(item => {
    item.addEventListener('mouseenter', mouseEnterAction);
    item.addEventListener('mouseleave', mouseLeaveAction);
})

function mouseEnterAction(e) {
    var target = e.target;
    target.classList.remove('mt-4', 'bg-light')
    target.classList.add('shadow-lg', 'mt-3', 'bg-white' ,'text-info');
}

function mouseLeaveAction(e) {
    var target = e.target;
    target.classList.remove('shadow-lg', 'mt-3', 'bg-white' ,'text-info');
    target.classList.add('mt-4', 'bg-light');
}