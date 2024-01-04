import { CREATE_COMMENT, DELETE_COMMENT, GET_POST_COMMENT, LIKE_COMMENT, UNLIKE_COMMENT } from "./ActionType";

const initialValue = {
    createdComment: null,
    postComment: null,
    likeComment: null,
    unlikeComment: null,
    deleteComment: null
};

export const commentReducer = (state = initialValue, { type, payload }) => {
    switch (type) {
        case CREATE_COMMENT:
            return {
                ...state,
                createdComment: payload
            };
        case GET_POST_COMMENT:
            return {
                ...state,
                postComment: payload
            };
        case LIKE_COMMENT:
            return {
                ...state,
                likeComment: payload
            };
        case UNLIKE_COMMENT:
            return {
                ...state,
                unlikeComment: payload
            };
        case DELETE_COMMENT:
        return {
            ...state,
            deleteComment: payload
        };
        default:
            return state; 
    }
};
