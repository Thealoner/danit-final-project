const resizeInput = (el) => {
  const events = 'keyup,keypress,focus,blur,change,input'.split(',');
  const spanEl = document.createElement('span');
  spanEl.className = 'span-helper';
  spanEl.innerHTML = el.value;
  el.after(spanEl);
  el.style.width = spanEl.clientWidth + 1 + 'px';
  spanEl.remove();

  events.forEach(function (item) {
    el.addEventListener(item, function () {
      const spanEl = document.createElement('span');
      spanEl.className = 'span-helper';
      spanEl.innerHTML = el.value;
      el.after(spanEl);
      el.style.width = spanEl.clientWidth + 1 + 'px';
      spanEl.remove();
    });
  });
};

export default resizeInput;