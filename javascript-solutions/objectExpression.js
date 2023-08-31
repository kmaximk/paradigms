"use strict"

let ExpressionPrototype = {
    evaluate: function (...args) {
        return this.count(...this.args.map((x) => x.evaluate(...args)));
    },
    toString: function () {
        let result = this.args.reduce(function (str, current) {
            return str + current.toString() + " ";
        }, "");
        return result + this.getSign();
    },
    prefix: function () {
        let result = this.args.reduce(function (str, current) {
            return str + " " + current.prefix();
        }, "");
        if (this.args.length === 0) {
            result = " ";
        }
        return "(" + this.getSign() + result + ")";
    }
}

function createOperation(count, getSign) {
    let prototype = Object.create(ExpressionPrototype);
    let f = function (...args) {
        prototype.count = count;
        prototype.getSign = getSign;
        this.args = args;
    }
    f.prototype = prototype;
    return f;
}

function Const(x) {
    this.x = x;
}

Const.prototype.evaluate = function (...args) {
    return this.x;
}
Const.prototype.toString = function () {
    return this.x.toString();
}
Const.prototype.prefix = function () {
    return this.x.toString();
}

function Variable(x) {
    this.x = x;
}

Variable.prototype.evaluate = function (...args) {
    return args[variables[this.x]];
};
Variable.prototype.toString = function () {
    return this.x;
}
Variable.prototype.prefix = function () {
    return this.x;
}
const variables = {
    "x": 0,
    "y": 1,
    "z": 2
}
let Add = createOperation((a, b) => (a + b), () => "+");
Add.prototype.constructor = Add;
let Subtract = createOperation((a, b) => (a - b), () => "-");
Subtract.prototype.constructor = Subtract;
let Divide = createOperation((a, b) => (a / b), () => "/");
Divide.prototype.constructor = Divide;
let Multiply = createOperation((a, b) => (a * b), () => "*");
Multiply.prototype.constructor = Multiply;
let Negate = createOperation((a) => -a, () => "negate");
Negate.prototype.constructor = Negate;
let Exp = createOperation(Math.exp, () => "exp");
Exp.prototype.constructor = Exp;
let Ln = createOperation(Math.log, () => "ln");
Ln.prototype.constructor = Ln;
let Sum = createOperation((...vars) => vars.reduce((prev, curr) => prev + curr, 0), () => "sum");
Sum.prototype.constructor = Sum;
let Avg = createOperation((...vars) => Sum.prototype.count(...vars) / (vars.length === 0 ? 1 : vars.length), () => "avg");
Avg.prototype.constructor = Avg;


const operations = {
    "+": [Add, 2],
    "-": [Subtract, 2],
    "*": [Multiply, 2],
    "/": [Divide, 2],
    "negate": [Negate, 1],
    "exp": [Exp, 1],
    "ln": [Ln, 1],
    "sum": [Sum, 0],
    "avg": [Avg, 0]
}

const isVariable = (str) => str === "x" || str === "y" || str === "z";

function parse(str) {
    str = str.trim().split(/\s+/);
    return (str.reduce(function (stack, v) {
        if (!isNaN(v)) {
            stack.push(new Const(parseInt(v)));
        } else if (isVariable(v)) {
            stack.push(new Variable(v));
        } else if (v in operations) {
            let args = stack.slice(-operations[v][1]);
            let ant = new operations[v][0](...args);
            stack.length -= operations[v][1];
            stack.push(ant)
        }
        return stack;
    }, []))[0];
}

function Src(str) {
    this.str = str;
    this.it = 0;
}

Src.prototype.hasNext = function () {
    return this.it < this.str.length;
}
Src.prototype.test = function (chr) {
    return chr === this.str[this.it];
}
Src.prototype.skipWhitespace = function () {
    while (this.test(" ")) {
        this.it++;
    }
}

function createError(name) {
    let prototype = Object.create(Error.prototype);

    function F(str) {
        Error.call(this, str);
    }

    F.prototype = prototype;
    F.prototype.name = name;
    return F;
}

let WrongArgumentError = createError("WrongArgumentError");
WrongArgumentError.prototype.constructor = WrongArgumentError;
let BracketError = createError("BracketError");
BracketError.prototype.constructor = BracketError;
let NumberOfArgsError = createError("NumberOfArgsError");
NumberOfArgsError.prototype.constructor = NumberOfArgsError;

function parsePrefix(str) {
    let src = new Src(str);
    let ans = parseNext(src, 0);
    if (src.hasNext()) {
        throw new NumberOfArgsError("Odd arguments");
    }
    return ans;
}

function parseNext(src, brc) {
    if (!src.hasNext()) {
        throw new NumberOfArgsError("Reached end of the string, but argument expected");
    }
    src.skipWhitespace();
    if (src.test("(")) {
        src.it++;
        let res = parseNext(src, 0);
        if (res instanceof Variable || res instanceof Const) {
            throw new BracketError("Bracket not expected " + src.it);
        }
        if (src.hasNext() && src.test(")")) {
            src.it++;
        } else {
            throw new BracketError("No closing bracket at " + src.it);
        }
        src.skipWhitespace();
        return res;
    }
    let s = "";
    while (src.hasNext() && !src.test("(") && !src.test(" ") && !src.test(")")) {
        s += src.str[src.it];
        src.it++;
    }
    if (s.length === 0) {
        throw new BracketError("Unexpected closing bracket at " + src.it);
    }
    src.skipWhitespace();
    if (s in operations) {
        if (brc) {
            throw new BracketError("Operation must be in brackets at " + src.it);
        }
        let args = [];
        for (let j = 0; j < operations[s][1]; j++) {
            args.push(parseNext(src));
        }
        if (operations[s][1] === 0) {
            while (src.hasNext() && !src.test(")")) {
                args.push(parseNext(src, 1));
            }
            return new operations[s][0](...args);
        }
        return new operations[s][0](...args);
    } else if (isVariable(s)) {
        return new Variable(s);
    } else if (!isNaN(s)) {
        return new Const(parseInt(s));
    } else {
        throw new WrongArgumentError("Unexpected argument at index " + src.it);
    }
}
// let x = parsePrefix("(x)");
// println(x);