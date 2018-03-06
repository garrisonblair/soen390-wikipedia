(function () {

    var selection = window.getSelection();
    var nodes = getNodesInSelection(selection);
    var referenceNumbers = getReferenceNumbers(nodes);

    return selection.toString();
})();

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

