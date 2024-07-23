
const article = document.querySelector('.article');
const contents = article.querySelector('.contents');
const buttons = article.querySelector('.buttons');

const numOfContent = 171;
const showContent = 10;
const showButton = 5;
let page = 1;
const maxPage = Math.ceil(numOfContent / showContent);

// Create content list
const makeContent = (id) => {
  const content = document.createElement('li');
  content.classList.add('content');
  content.innerHTML = `<span class="content__seq">${id}</span>
          <span class="content__writer">writer</span>
          <span class="content__title">title</span>
          <span class="content__date">date</span>
          <span class="content__see">see</span>`;
  return content;
};

const makeButton = (id) => {
  const button = document.createElement('button');
  button.classList.add('button');
  button.dataset.num = id;
  button.innerText = id;
  button.addEventListener('click', (evt) => {
    Array.prototype.forEach.call(buttons.children, (button) => {
      if (button.dataset.num) {
        button.classList.remove('active');
      }
    });
    evt.target.classList.add('active');
    renderContent(parseInt(evt.target.dataset.num));
  });
  return button;
};

const renderContent = (page) => {
  while (contents.hasChildNodes()) {
    contents.removeChild(contents.lastChild);
  }
  for (
      let id = (page - 1) * showContent + 1;
      id <= page * showContent && id <= numOfContent;
      id++
  ) {
    contents.appendChild(makeContent(id));
  }
};

const renderButton = (page) => {
  while (buttons.hasChildNodes()) {
    buttons.removeChild(buttons.lastChild);
  }
  for (let id = page; id < page + showButton && id <= maxPage; id++) {
    buttons.appendChild(makeButton(id));
  }
  buttons.children[0].classList.add('active');
  buttons.prepend(prev);
  buttons.append(next);

  if (page - showButton < 1) buttons.removeChild(prev);
  if (page + showButton > maxPage) buttons.removeChild(next);
};

const render = (page) => {
  renderContent(page);
  renderButton(page);
};

const goPrevPage = () => {
  if (page > 1) {
    page -= showButton;
    if (page < 1) page = 1;
    render(page);
  }
};

const goNextPage = () => {
  if (page < maxPage) {
    page += showButton;
    if (page > maxPage) page = maxPage;
    render(page);
  }
};

const prev = document.createElement('button');
prev.classList.add('button', 'prev');
prev.innerHTML = '<ion-icon name="chevron-back-outline"></ion-icon>';
prev.addEventListener('click', goPrevPage);

const next = document.createElement('button');
next.classList.add('button', 'next');
next.innerHTML = '<ion-icon name="chevron-forward-outline"></ion-icon>';
next.addEventListener('click', goNextPage);

render(page);