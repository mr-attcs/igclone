import React, { useEffect, useState } from 'react';
import StoryCircle from '../../Components/Story/StoryCircle';
import HomeRight from '../../Components/HomeRight/HomeRight';
import PostCard from '../../Components/Post/PostCard';
import { useDispatch, useSelector } from 'react-redux';
import { findUserPostAction } from '../../Redux/Post/Action';
import {findUsersByUserIdsAction, /* getPopularUser,  */getUserProfileAction} from '../../Redux/User/Action';
/* import { useNavigate } from 'react-router-dom'; */
import { hasStory } from '../../Config/Logics';

const HomePage = () => {
    const [userIds, setUserIds] = useState();
    const {user, post} = useSelector(store => store);
    const token = localStorage.getItem('token');
    /* const reqUser = useSelector(store => store);
    const [suggestedUser, setSuggestedUser] = useState([]); */
    /* const navigate = useNavigate(); */

    const dispatch = useDispatch();
      
    useEffect(() => {

        const newIds = user.reqUser?.following?.map((user) => user.id);
        if(newIds?.length>0){
            setUserIds([user.reqUser?.id, ...newIds]);
        }
        else setUserIds([user.reqUser?.id])
    }, [user.reqUser])


    useEffect(() => {
        const data = {
            jwt: token,
            userIds: [userIds].join(',')
        };
        dispatch(findUserPostAction(data));
        dispatch(findUsersByUserIdsAction(data))
        /*dispatch(getPopularUser(token)) */
    }, [userIds, post.createdPost, post.deletedPost, token, dispatch]);

    useEffect(() => {
        dispatch(getUserProfileAction(token))
    },[token, dispatch])

    const storyUsers = hasStory(user.findUsersByIds)

    return (
        <div>
          <div className='mt-10 flex w-[100%] justify-center'>
              <div className='w-[44%] px-10'>
                  <div className='storyDiv flex space-x-2 border p-4 rounded-md justify-start w-full'>
                      {storyUsers.length>0 && storyUsers.map((item) => (
                      <StoryCircle user={item}/>
                      ))}
                  </div>

                  <div className='space-y-10 w-full mt-10'>
                      {post.usersPost.length>0 && post.usersPost.map((post) => (
                      <PostCard post={post} />
                      ))}
                  </div>
              </div>
              <div className='w-[27%]'>
                  <HomeRight user = {user} />
              </div>
          </div>
      </div>
  );
};

export default HomePage;