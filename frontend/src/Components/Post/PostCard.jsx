import React, {useEffect, useState} from 'react'
import {BsBookmark, BsBookmarkFill, BsEmojiSmile, BsThreeDots} from 'react-icons/bs'
import {AiFillHeart, AiOutlineHeart} from 'react-icons/ai'
import {FaRegComment} from 'react-icons/fa'
import "./PostCard.css"
import {RiSendPlaneLine} from 'react-icons/ri';
import CommentModal from '../Comment/CommentModal'
import {useDisclosure} from '@chakra-ui/react'
import {useDispatch, useSelector} from 'react-redux'
import {deletePostAction, likePostAction, savePostAction, unlikePostAction, unsavePostAction} from '../../Redux/Post/Action'
import {isPostLikedByUser, isSavedPost} from '../../Config/Logics'
import { useNavigate } from 'react-router-dom'

const PostCard = ({post}) => {
    const [showDropDown, setShowDropDown] = useState(false);
    const [isPostLiked, setIsPostLiked] = useState(false);
    const [isSaved, setIsSaved] = useState(false);
    const {isOpen, onOpen, onClose} = useDisclosure();
    const {user} = useSelector(store => store);
    const dispatch = useDispatch();
    const token = localStorage.getItem("token");
    const navigate = useNavigate();

    const data = {
        jwt: token,
        postId: post
            ?.id
    }

    const handleSavePost = () => {
        setIsSaved(true)
        dispatch(savePostAction(data))
    }

    const handleUnsavePost = () => {
        setIsSaved(false)
        dispatch(unsavePostAction(data))
    }

    const handlePostLike = () => {
        setIsPostLiked(true)
        dispatch(likePostAction(data))
    }

    const handlePostUnlike = () => {
        setIsPostLiked(false)
        dispatch(unlikePostAction(data))
    }

    const handlePostDelete = () => {
        dispatch(deletePostAction(data))
    }

    const handleClick = () => {
        setShowDropDown(!showDropDown);
    }

    const handleOpenCommentModal = () => {
        navigate(`/comment/${post.id}`)

       

        onOpen()
    };

    useEffect(() => {
        setIsPostLiked(isPostLikedByUser(post, user.reqUser?.id))
        setIsSaved(isSavedPost(user.reqUser, post.id))
    }, [post, user.reqUser])

    return (
        <div>
            <div className='border rounded-md w-full mb-14'>
                <div className='flex justify-between items-center w-full py-4 px-5'>
                    <div className='flex items-center'>                    
                        <img
                            className='h-12 w-12 rounded-full'
                            src={post?.user.userimage  || "https://i.ibb.co/3h581Xp/Adobe-Stock-349497933.jpg"}
                            alt=""/>
                        <div className='pl-2'>
                            <p className='font-semibold text-sm items-center'>{post?.user.username}</p>
                            <p className='font-thin texst-sm'>{post?.location}</p>
                        </div>
                    </div>

                    <div className='dropdown'>
                        <BsThreeDots className='dots' onClick={handleClick}/>
                        <div className='dropdown-content'>
                            {showDropDown && <p onClick={handlePostDelete} className='bg-black text-white py-1 px-4 rounded-md cursor-pointer'>Delete</p>}
                        </div>
                    </div>

                </div>

                <div className='w-full'>
                    <img className='w-full' src={post
                            ?.image} alt=""/>
                </div>

                <div className='flex justify-between items-center w-full px-5 py-4'>
                    <div className='flex items-center space-x-2 '>
                            {isPostLiked ? (
                                <AiFillHeart
                                    className='text-3xl hover:opacity-50 cursor-pointer text-red-600'
                                    onClick={() => handlePostUnlike(data.postId)}
                                />
                            ) : (   
                                <AiOutlineHeart
                                    className='text-2xl hover:opacity-50 cursor-pointer'
                                    onClick={() => handlePostLike(data.postId)}
                                />
                                )}

                        <FaRegComment
                            onClick={handleOpenCommentModal}
                            className='text-xl hover:opacity-50 cursor-pointer'/>
                        <RiSendPlaneLine className='text-2xl hover:opacity-50 cursor-pointer'/>

                    </div>
                    <div className='cursor-pointer'>
                        {isSaved ? (
                                    <BsBookmarkFill
                                        onClick={handleUnsavePost}
                                        className='text-xl hover:opacity-50 cursor-pointer'
                                        />
                                ) : (
                                    <BsBookmark
                                        onClick={handleSavePost}
                                        className='text-xl hover:opacity-50 cursor-pointer'/>
                                )}
                    </div>
                </div>
                <div className='w-full py-2 px-5'>
                    {post?.likedByUsers?.length > 0 && <p>{post?.likedByUsers?.length} likes</p>}
                    {post?.comments?.length > 0 && <p className='opacity-50 py-2 cursor-pointer'>View all {post?.comments?.length} comments</p>}
                </div>

                <div className='border border-t w-full'>
                    <div className='flex w-full items-center px-5'>
                        <BsEmojiSmile/>
                        <input className='commentInput' type='text' placeholder='Add a comment...'/>
                    </div>
                </div>
            </div>

            <CommentModal
                handlePostLike={handlePostLike}
                onClose={onClose}
                isOpen={isOpen}
                handleSavePost={handleSavePost}
                isPostLiked={isPostLiked}
                isSaved={isSaved}/>
        </div>
    )

}
export default PostCard