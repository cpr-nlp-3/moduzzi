const puppeteer = require("puppeteer");

require("dotenv").config();

const crawler = async (lecture, professor) => {
  const browser = await puppeteer.launch({ headless: false });
  const page = await browser.newPage();
  const everytimeId = process.env.EVERYTIME_ID;
  const everytimePw = process.env.EVERYTIME_PW;
  const result = [];

  await page.goto("https://everytime.kr/lecture");
  await page.waitForTimeout(1000);

  await page.type(
    "body > div:nth-child(2) > div > form > div.input > input[type=text]:nth-child(1)",
    everytimeId
  );
  await page.type(
    "body > div:nth-child(2) > div > form > div.input > input[type=password]:nth-child(2)",
    everytimePw
  );
  await page.click("body > div:nth-child(2) > div > form > input[type=submit]");
  await page.waitForTimeout(1000);

  await page.type(
    "body > div > div > div.side > div > form > input[type=search]:nth-child(1)",
    lecture
  );
  await page.click("body > div > div > div.side > div > form > input.submit");
  await page.waitForTimeout(1000);

  const lectureElementHandle = await page.evaluateHandle((professor) => {
    const elements = document.querySelectorAll(
      "body > div > div > div.lectures > a.lecture > div.professor"
    );

    for (const element of elements) {
      if (element.textContent.includes(`${professor}`)) {
        return element;
      }
    }

    return null;
  }, professor);

  if (!lectureElementHandle) {
    await browser.close();

    console.log(`${professor} 교수님의 ${lecture} 강의가 없습니다.`);

    return;
  }

  const lectureElement = lectureElementHandle.asElement();

  await lectureElement.click();
  await page.waitForTimeout(1000);

  const moreElementHandle = await page.evaluateHandle(() => {
    const element = document.querySelector(
      "body > div > div > div.pane > div > section.review > div.articles > a"
    );

    return element;
  });

  if (!moreElementHandle) {
    await browser.close();

    console.log(
      `${professor} 교수님의 ${lecture} 강의에 대한 후기가 없습니다.`
    );

    return;
  }

  const moreElement = moreElementHandle.asElement();

  await moreElement.click();
  await page.waitForTimeout(1000);

  await page
    .evaluate(() => {
      const elements = document.querySelectorAll(
        "body > div > div > div.pane > div > div.articles > div.article > div.text"
      );

      const temp = [];

      for (const element of elements) {
        temp.push(element.textContent);
      }

      return temp;
    })
    .then((resultArray) => {
      result.push(...resultArray);
    });

  await browser.close();

  result.forEach((text, index) => {
    console.log(`${index + 1}. ${text}\n`);
  });

  return;
};

crawler("자료구조", "장부루");
