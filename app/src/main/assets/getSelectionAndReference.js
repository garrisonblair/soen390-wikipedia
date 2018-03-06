(function () {

    var selection = window.getSelection();
    getNodesInSelection(selection);
    return selection.rangeCount
})();

function getNodesInSelection(selection) {
    var content = [document.getElementById("content")];
    content = content[0];
    var nodes = [];

    getNodesInSelectionRecursive(content, selection, nodes, 0, null);

    return [];
}

function getNodesInSelectionRecursive(node, selection, nodes, phase, endNode) {
    for (var i = 0; i < node.childNodes.length; i++) {
        var childNode = node.childNodes[i];
        if(phase == 0) {
            if(childNode == selection.anchorNode) {
                endNode = selection.focusNode;
                nodes.push(childNode);
                phase = 1;
            } else if (childNode == selection.focusNode) {
                endNode = selection.anchorNode;
                nodes.push(childNode);
                phase = 1;
            } else {
                phase = getNodesInSelectionRecursive(childNode, selection, nodes, phase, endNode);
            }
        } else if (phase == 1) {
            nodes.push(childNode);
            if (childNode == endNode) {
                phase = 2;
                return phase;
            }
            phase = getNodesInSelectionRecursive(childNode, selection, nodes, phase, endNode);

        } else if (phase == 2) {
            return phase;
        }
    }

    return phase
}

//# sourceURL=getSelectionAndReferences.js

