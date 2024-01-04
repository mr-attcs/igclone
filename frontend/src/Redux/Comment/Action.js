import {CREATE_COMMENT, DELETE_COMMENT, GET_POST_COMMENT, LIKE_COMMENT, UNLIKE_COMMENT} from "./ActionType";

const BASE_URL = "http://localhost:5454/api";

export const createCommentAction = (data) => async (dispatch) => {
    try {

        const res = await fetch(`${BASE_URL}/comments/create/${data.postId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + data.jwt
            },
            body: JSON.stringify(data.data)
        })

        const comment = await res.json();
        console.log("Created comment " + comment)
        dispatch({type: CREATE_COMMENT, payload: comment})

    } catch (error) {
        console.log("catch ", error)
    }

}

export const findPostCommentAction = (data) => async (dispatch) => {

    try {

        const res = await fetch(`${BASE_URL}/comments/${data.postId}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + data.jwt
            }
        })

        const comment = await res.json();
        console.log("Find Post Comment " + comment)
        dispatch({type: GET_POST_COMMENT, payload: comment})

    } catch (error) {
        console.log("catch ", error)
    }

}

export const likeCommentAction = (data) => async (dispatch) => {

    try {

        const res = await fetch(`${BASE_URL}/comments/like/${data.commentId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer  " + data.jwt
            }
        })

        const comment = await res.json();
        console.log("Liked comment " + comment)
        dispatch({type: LIKE_COMMENT, payload: comment})

    } catch (error) {
        console.log("catch ", error)
    }

}

export const unlikeCommentAction = (data) => async (dispatch) => {

    try {

        const res = await fetch(`${BASE_URL}/comments/unlike/${data.commentId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + data.jwt
            }
        })

        const comment = await res.json();
        console.log("Unliked comment " + comment)
        dispatch({type: UNLIKE_COMMENT, payload: comment})

    } catch (error) {
        console.log("catch ", error)
    }


    
}

export const deleteCommentAction = (data) => async (dispatch) => {

    try {

        const res = await fetch(`${BASE_URL}/comments/delete/${data.commentId}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + data.jwt
            }
        })

        const comment = await res.json();
        console.log("Deleted comment " + comment)
        dispatch({type: DELETE_COMMENT, payload: comment})

    } catch (error) {
        console.log("catch ", error)
    }


    
}