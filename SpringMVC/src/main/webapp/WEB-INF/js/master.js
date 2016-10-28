/**
 * Format comma number
 */
function comma(num) {
    var n = num.toString(), p = n.indexOf('.');
    return n.replace(/\d(?=(?:\d{3})+(?:\.|$))/g, function($0, i) {
        return p < 0 || i < p ? ($0 + ',') : $0;
    });
}

/**
 * Add zero before number
 * Example
 *      zeroPad(5, 6); // "000005"
 *      zeroPad(1234, 2); // "1234" :)
 */
function zeroPad(num, places) {
    var zero = places - num.toString().length + 1;
    return Array(+(zero > 0 && zero)).join("0") + num;
}