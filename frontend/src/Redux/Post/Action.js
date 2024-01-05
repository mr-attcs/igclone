import {
    CREATE_NEW_POST,
    DELETE_POST,
    GET_SINGLE_POST,
    GET_USER_POST,
    LIKE_POST,
    REQ_USER_POST,
    SAVE_POST,
    UNLIKE_POST,
    UNSAVE_POST
  } from "./ActionType";
  
  const BASE_URL = "http://13.48.149.190:8080/api";
  
  export const createPostAction = (data) => async (dispatch) => {
    try{
        const res = await fetch(`${BASE_URL}/posts/create`, {
            method: "POST",
            headers: {
                "Content-Type" : "application/json",
                Authorization: "Bearer " + data.jwt
            },
            body: JSON.stringify(data.data)
        });
      const post = await res.json();
      console.log("created post: ", post);
      dispatch({ type: CREATE_NEW_POST, payload: post });
    } catch (error) {
      console.log("Catch ", error);
    }
  };
  
  export const findUserPostAction = (data) => async (dispatch) => {
    if (data && data.userIds) {
      try {
        const res = await fetch(`${BASE_URL}/posts/following/${data.userIds}`, {
            method: "GET",
            headers: {
                "Content-Type" : "application/json",
                Authorization: "Bearer " + data.jwt
            }
        });
      const posts = await res.json();

      dispatch({ type: GET_USER_POST, payload: posts });

    } catch (error) {
      console.log("Catch ", error);
    }
    }
  };
  
  export const reqUserPostAction = (data) => async (dispatch) => {
    if (!data.userId) {
      return;
    }
    try {
        const res = await fetch(`${BASE_URL}/posts/following/${data.userId}`, {
            method: "GET",
            headers: {
                "Content-Type" : "application/json",
                Authorization: "Bearer " + data.jwt
            }
        });
      const posts = await res.json();
      console.log("Req User's Post ", posts);
      dispatch({ type: REQ_USER_POST, payload: posts });
    } catch (error) {
      console.log("Catch ", error);
    }
  };
  
  export const likePostAction = (data) => async (dispatch) => {
    try {
        const res = await fetch(`${BASE_URL}/posts/like/${data.postId}`, {
            method: "PUT",
            headers: {
                "Content-Type" : "application/json",
                Authorization: "Bearer " + data.jwt
            }
        });

      const post = await res.json();

      console.log("Liked Post: ", post);

      dispatch({ type: LIKE_POST, payload: post });

    } catch (error) {
      console.log("Catch ", error);
    }
  };
  
  export const unlikePostAction = (data) => async (dispatch) => {
    try {
        const res = await fetch(`${BASE_URL}/posts/unlike/${data.postId}`, {
            method: "PUT",
            headers: {
                "Content-Type" : "application/json",
                Authorization: "Bearer " + data.jwt
            }
        });
      const post = await res.json();
      console.log("Unliked Post: ", post);
      dispatch({ type: UNLIKE_POST, payload: post });
    } catch (error) {
      console.log("Catch ", error);
    }
  };

  
  export const deletePostAction = (data) => async (dispatch) => {
    try {
        const res = await fetch(`${BASE_URL}/posts/delete/${data.postId}`, {
            method: "DELETE",
            headers: {
                "Content-Type" : "application/json",
                Authorization: "Bearer " + data.jwt
            }
        });
      const post = await res.json();
      console.log("Deleted Post: ", post);
      dispatch({ type: DELETE_POST, payload: post });
    } catch (error) {
      console.log("Catch ", error);
    }
  };
  
  export const savePostAction = (data) => async (dispatch) => {
    try {
        const res = await fetch(`${BASE_URL}/posts/save_post/${data.postId}`, {
            method: "PUT",
            headers: {
                "Content-Type" : "application/json",
                Authorization: "Bearer " + data.jwt
            }
        });
      const post = await res.json();
      console.log("Saved Post: ", post);
      dispatch({ type: SAVE_POST, payload: post });
    } catch (error) {
      console.log("Catch ", error);
    }
  };
  
  export const unsavePostAction = (data) => async (dispatch) => {
    try {
        const res = await fetch(`${BASE_URL}/posts/unsave_post/${data.postId}`, {
            method: "PUT",
            headers: {
                "Content-Type" : "application/json",
                Authorization: "Bearer " + data.jwt
            }
        });
      const post = await res.json();
      console.log("Unsaved Post: ", post);
      dispatch({ type: UNSAVE_POST, payload: post });
    } catch (error) {
      console.log("Catch ", error);
    }
  };
  
  export const findPostByIdAction = (data) => async (dispatch) => {
    try {
        const res = await fetch(`${BASE_URL}/posts/${data.postId}`, {
            method: "GET",
            headers: {
                "Content-Type" : "application/json",
                Authorization: "Bearer " + data.jwt
            }
        });
      const post = await res.json();
      console.log("Get Single Post: ", post);
      dispatch({ type: GET_SINGLE_POST, payload: post });
    } catch (error) {
      console.log("Catch ", error);
    }
  };
  