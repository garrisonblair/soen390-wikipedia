(function () {

    try {
        var selection = window.getSelection();
        var nodes = getNodesInSelection(selection);
        var referenceNumbers = getReferenceNumbers(nodes);
        var references = getReferenceTexts(referenceNumbers);
        var result = {selectionText: selection.toString(), references: references};
    } catch(err) {
        console.log(err.message);
        return {selectionText: selection.toString(), references: []};
    }


    return result
})();

function getReferenceTexts(refNumbers) {
    references = [];
    referenceList = null;
    try{
        referenceList = document.getElementById("References").nextSibling.getElementsByClassName("mw-references")[0].childNodes;

    } catch(err) {
        console.log(err.message);
    }

    if (!referenceList) {
        referenceList = referenceList = document.getElementById("Footnotes").nextSibling.getElementsByClassName("mw-references")[0].childNodes;
    }

    for(var i = 0; i < refNumbers.length; i++) {
        var node = referenceList[refNumbers[i] - 1];
        text = getReferenceTextRecursive(node, "");
        references[i] = {number: refNumbers[i], text: text}
    }

    return references;
}

//find all text elements in the list node and concatenate their data
function getReferenceTextRecursive(node, text) {
    if (node.nodeName == "#text") {
        return text + node.data;
    } else {
        var childNodes = node.childNodes;
        if (childNodes) {
            for (var i = 0; i < childNodes.length; i++) {
                text = getReferenceTextRecursive(childNodes[i], text);
            }
        }
    }
    return text
}

// return all numbers for references. Makes the assumption that the node of a reference has the class "mw-ref"
function getReferenceNumbers(nodes) {
    var referenceNumbers = [];

    for(var i = 0; i < nodes.length; i++) {
        node = nodes[i];
        if (node.classList && node.classList.contains("mw-ref")) {
            var number = node.childNodes[0].childNodes[0].innerHTML

            number = Number(number.substring(1, number.length - 1));
            if(referenceNumbers.indexOf(number) < 0){
                referenceNumbers.push(number);
            }
        }
    }

    return referenceNumbers;
}

//get all nodes between the anchorNode and the focusNode
function getNodesInSelection(selection) {

    var content = document.getElementById("content");
    var nodes = [];

    if (selection.anchorNode == selection.focusNode) {
        nodes.push(selection.anchorNode);
        if (selection.anchorNode.nextSibling) {
            nodes.push(selection.anchorNode.nextSibling);
        }
        return nodes;
    }

    getNodesInSelectionRecursive(content, selection, nodes, {phase: 0, endNode: null});

    return nodes;
}


//traverse DOM tree recursively and add appropriate nodes to array.
//node: node to start at
//selection: the selection object for which we need to nodes
//nodes: array in which to store result
//phase: phase of the algorithm (0, 1, 2)
//endNode: once a boundary node has been reached, the endNode is a reference to the other boundary node
function getNodesInSelectionRecursive(node, selection, nodes, state) {
    for (var i = 0; i < node.childNodes.length; i++) {
        var childNode = node.childNodes[i];
        if(state.phase == 0) {
            //phase = 0: have not reach a boundary node (focusNode or anchorNode) yet, do not add nodes
            if(childNode == selection.anchorNode) {
                state.endNode = selection.focusNode;
                nodes.push(childNode);
                state.phase = 1;
            } else if (childNode == selection.focusNode) {
                state.endNode = selection.anchorNode;
                nodes.push(childNode);
                state.phase = 1;
            } else {
                state = getNodesInSelectionRecursive(childNode, selection, nodes, state);
            }
        } else if (state.phase == 1) {
            //phase = 1: the first boundary node has been reached, all visited nodes are now added.
            nodes.push(childNode);
            if (childNode == state.endNode) {
                if(childNode.nextSibling) {
                    //add the node after the endNode to include trailing references
                    nodes.push(childNode.nextSibling);
                }
                state.phase = 2;
                return state;
            }
            state = getNodesInSelectionRecursive(childNode, selection, nodes, state);

        } else if (state.phase == 2) {
            //phase = 2: the second boundary node has been visited, the algorithm can end.
            return state;
        }
    }

    return state
}

//# sourceURL=getSelectionAndReferences.js

