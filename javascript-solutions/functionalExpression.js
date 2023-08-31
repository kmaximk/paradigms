"use strict"
const count = (f) => (...args) => (...vars) => f(...args.map(x => x(...vars)));
const add = count((a, b) => a + b);
const subtract = count((a, b) => a - b);
const multiply = count((a, b) => a * b);
const divide = count((a, b) => a / b);
const sin = count(Math.sin);
const cos = count(Math.cos);
const negate = count((a) => -a);
const argMin3 = count((...args) => Math.min(...args));
const argMin5 = count((...args) => Math.min(...args));
const argMax3 = count((...args) => Math.max(...args));
const argMax5 = count((...args) => Math.max(...args));
const madd = count((a, b, c) => a * b + c);
const floor = Math.floor;
const ceil = Math.ceil;
const cnst = (num) => (x, y, z) => num;
const one = cnst(1);
const two = cnst(2);
const variable = (str) => (...args) => ((str === "x") * args[0] + (str === "y") * args[1] + (str === "z") * args[2]);
const isVariable = (str) => str === "x" || str === "y" || str === "z";
const operations = {
    "+": [add, 2],
    "-": [subtract, 2],
    "*": [multiply, 2],
    "/": [divide, 2],
    "argMin5" : [argMin5, 5],
    "argMax5" : [argMax5, 5],
    "sin" : [sin, 1],
    "cos" : [cos, 1],
    "negate" : [negate, 1],
    "argMin3" : [argMin3, 3],
    "argMax3" : [argMax3, 3],
    "*+": [madd, 3],
    "_": [floor, 1],
    "^": [ceil, 1],
};
const constants = {
    "one" : one,
    "two" : two,
}
function parse(str) {
    str = str.trim().split(/\s+/);
    return (str.reduce(function (stack, v) {
        if (!isNaN(v)) {
            stack.push(cnst(parseInt(v)));
        } else if (isVariable(v)) {
            stack.push(variable(v));
        } else if (v in operations) {
            let args = stack.slice(-operations[v][1]);
            let ant = operations[v][0](...args);
            stack.length -= operations[v][1];
            stack.push(ant)
        } else {
            stack.push(constants[v]);
        }
        return stack;
    }, []))[0];
}
println([1, 2, 3].slice(-0));
let exp = cos(cnst(-0.5));
for (let i = 0; i < 10; i++) {
    println(exp(i, 0, 0));
}

