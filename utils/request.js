const request = function (obj) {
    let key = 'cookie'
    obj.cookie = wx.getStorageSync(key);
    if (!('fail' in obj)) {
        obj.fail = function (err) {
        }
    }
    if (!('complete' in obj)) {
        obj.complete = function (res) {
        }
    }
    obj.header ={
        "Content-Type":"application/json",
    }
    if(obj.cookie){
        obj.header["Cookie"] = obj.cookie;
    }
    wx.request({
        url: obj.url,
        data: JSON.stringify(obj.data),
        method: obj.method,
        header: obj.header,
        success: res => {
            if (res.header) {
                if ('Set-Cookie' in res.header) {
                    console.log(res.header['Set-Cookie'].split(";")[0])
                    wx.setStorageSync(key, res.header['Set-Cookie'].split(";")[0]);
                }
                else if ('set-cookie' in res.header) {
                    wx.setStorageSync(key, res.header['set-cookie'].split(";")[0])
                }
            }
            obj.success(res);
        },
        fail: err => {
            if (res.header) {
                if ('Set-Cookie' in res.header) {
                    wx.setStorageSync(key, res.header['Set-Cookie'].split(";")[0]);
                }
                else if ('set-cookie' in res.header) {
                    wx.setStorageSync(key, res.header['set-cookie'].split(";")[0])
                }
            }
            obj.fail(err);
        },
        complete: res => {
            obj.complete(res);
        }
    });
}
module.exports = {
    request
}