import React, { useEffect } from 'react'
import StoryViewer from '../../Components/StoryComponents/StoryViewer'
import { useDispatch, useSelector } from 'react-redux'
import { useParams } from 'react-router-dom';
import { findStoryByUserId } from '../../Redux/Story/Action';

const Story = () => {
    const story = useSelector(store => store.story);
    const dispatch = useDispatch();
    const jwt = localStorage.getItem("token");
    const { userId } = useParams();

    useEffect(() => {
        const data = { jwt, userId };
        dispatch(findStoryByUserId(data))
    }, [userId, dispatch, jwt])

    return (
        <div>
            <StoryViewer stories={story?.stories}/>
        </div>
    )
}

export default Story
