import {SIGN_IN, SIGN_UP} from "./ActionType";

const handleSuccess = async (res, dispatch) => {
    const token = res.headers.get("Authorization");
    
    if (token) {
        localStorage.setItem('token', token);
        dispatch({ type: SIGN_IN, payload: token });
        console.log('Token:', token);
    } else {
        console.log('Token not found in headers');
    }
};


const handleError = (res) => {
    console.log(`HTTP error! Status: ${res.status}`);
};

export const signInAction = (data) => async (dispatch) => {

    const headers = new Headers({
        "Content-Type": "application/json",
        Authorization: `Basic ${btoa(`${data.email}:${data.password}`)}`
    })

    const res = await fetch('http://localhost:5454/signin', {
        method: "GET",
        headers: headers
    })

    if (res.ok) {
        handleSuccess(res, dispatch);
    } else {
        handleError(res);
    }
}

export const signUpAction = (data) => async (dispatch) => {
    try {
        const res = await fetch('http://localhost:5454/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (res.ok) {
            const user = await res.json();
            console.log("signup user:", user);
            dispatch({type: SIGN_UP, payload: user});
        } else {
            handleError(res);
        }
    } catch (error) {
        console.log(error);
    }
}
