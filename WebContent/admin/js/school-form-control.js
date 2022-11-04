/**
 * 入力値をもとにスクール機能のフォームの要素を制御するためのJavaScript
 */

const FULL_WIDTH_SPACE = "　";

function controlActivateOrInactivateForOption(idValue) {
    let bootstrapParentNode = document.getElementById(idValue).nextElementSibling.children[1];

    for (let i = 0; i < bootstrapParentNode.childElementCount - 1; i++) {
        let bootstrapChildNode = bootstrapParentNode.children[i];
        let bootstrapChildCharArray = bootstrapChildNode.innerText.split("");
        let bootstrapChildCountSpace = 0;
        for (let j = 0; j < bootstrapChildCharArray.length; j++) {
            if(FULL_WIDTH_SPACE === bootstrapChildCharArray[j]) {
                bootstrapChildCountSpace += 1;
            } else {
                break;
            }
        }

        let nextBootstrapChildNode = bootstrapChildNode.nextElementSibling;
        for (let k = 0; k < bootstrapParentNode.childElementCount - 1 - i; k++) {
            let nextBootstrapChildCharArray = nextBootstrapChildNode.title.split("");
            let nextBootstrapChildCountSpace = 0;
            for (let l = 0; l < nextBootstrapChildCharArray.length; l++) {
                if(FULL_WIDTH_SPACE === nextBootstrapChildCharArray[l]) {
                    nextBootstrapChildCountSpace += 1;
                } else {
                    break;
                }
            }

            if (bootstrapChildCountSpace < nextBootstrapChildCountSpace) {
                if (bootstrapChildNode.classList.contains("active") || bootstrapChildNode.disabled) {
                    if (nextBootstrapChildNode.children[0].children[0].checked) {
                        nextBootstrapChildNode.children[0].children[0].checked = false;
                        nextBootstrapChildNode.classList.remove("active");
                        let elemsList = document.getElementsByTagName("option");
                        for (let m = 0; m < elemsList.length; m++) {
                            if (elemsList[m].getAttribute("data-multiselectid")
                                === nextBootstrapChildNode.children[0].children[0].getAttribute("id")) {
                                elemsList[m].selected = false;
                                break;
                            }
                        }
                    }
                    nextBootstrapChildNode.disabled = true;
                } else {
                    nextBootstrapChildNode.disabled = false;
                }
                nextBootstrapChildNode = nextBootstrapChildNode.nextElementSibling;
            } else {
                break;
            }
        }
    }

}
