import {applyMiddleware, combineReducers, legacy_createStore} from "redux";
import {authReducer} from "../Auth/Reducer";
import thunk from "redux-thunk";
import {userReducer} from "../User/Reducer";
import {postReducer} from "../Post/Reducer";
import {commentReducer} from "../Comment/Reducer";
import { storyReducer } from "../Story/Reducer";

const rootReducers = combineReducers(
    {   
        auth: authReducer, 
        user: userReducer, 
        post: postReducer, 
        comment: commentReducer,
        story: storyReducer
    }
)

export const store = legacy_createStore(rootReducers, applyMiddleware(thunk));