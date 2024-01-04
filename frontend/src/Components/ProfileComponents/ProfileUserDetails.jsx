import React, { useEffect } from "react"
import {TbCircleDashed} from "react-icons/tb"
import { useDispatch, useSelector } from "react-redux"
import { useNavigate } from "react-router-dom";
import { reqUserPostAction } from "../../Redux/Post/Action";
import { Button } from '@chakra-ui/react';
import { followUserAction, unfollowUserAction } from "../../Redux/User/Action";
import { isReqUser } from "../../Config/Logics";

export const ProfileUserDetails = ({user, isFollowing, isRequser}) => {
    const navigate = useNavigate();
    const token = localStorage.getItem("token")
    const dispatch = useDispatch();
    const {post} = useSelector(store => store);

    const data = {
        jwt: token,
        userId: user
            ?.id
    }

    const followHandler = () => {
        dispatch(followUserAction(data))
    } 

    const unfollowHandler = () => {
        dispatch(unfollowUserAction(data))
    } 

    useEffect(() => {
        dispatch(reqUserPostAction(token))
    },[token, dispatch])
 
    return (
        <div className="py-10 w-full">
            <div className="flex items-center">
                <div className="w-[15%]">
                    <img
                        className="w-32 h-32 rounded-full"
                        src={user?.image || "https://i.ibb.co/3h581Xp/Adobe-Stock-349497933.jpg"}
                        alt=""/>
                </div>

                <div className="space-y-5">
                    <div className="flex space-x-10 items-center">
                        <p>{user?.username}</p>
                        <button onClick={() => navigate("/account/edit")}>Edit Profile</button>
                        <TbCircleDashed></TbCircleDashed>
                    </div>
                    <div className="flex space-x-10">
                        <div>
                        <span className="font-semibold mr-2">{post?.profilePost?.length}</span>
                        <span>posts</span>

                        </div>

                        <div>
                            <span className="font-semibold mr-2">{user?.follower?.length}</span>
                            <span>follower</span>
                        </div>
                        <div>
                            <span className="font-semibold mr-2">{user?.following?.length}</span>
                            <span>following</span>
                        </div>
                    </div>
                    <div>
                        <p className="font-semibold">{user?.name}</p>
                        <p className="font-thin text-sm">{user?.bio}</p>
                    </div>
                </div>
                    {!isRequser ? (
                        !isFollowing ? (
                            <Button colorScheme="blue" style={{ marginLeft: '50px', height: '30px' }} onClick={followHandler}>
                            Follow
                            </Button>
                        ) : (
                            <Button colorScheme="blue" style={{ marginLeft: '50px', height: '30px' }} onClick={unfollowHandler}>
                            Unfollow
                            </Button>
                        )
                        ) : (
                        null
                )}


            </div>
        </div>
    )
}
