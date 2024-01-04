import React, {useEffect, useState } from 'react'
import {AiFillHeart, AiOutlineHeart} from 'react-icons/ai'
import { isCommentLikedByUser, timeDifference } from '../../Config/Logics'
import { useDispatch, useSelector } from 'react-redux'
import { deleteCommentAction, likeCommentAction, unlikeCommentAction } from '../../Redux/Comment/Action'
import { BsThreeDots } from 'react-icons/bs'

const CommentCard = ({comment}) => {
    const [isCommentLiked, setIsCommentLiked] = useState();
    const dispatch = useDispatch();
    const token = localStorage.getItem("token");
    const {user} = useSelector((store) => store);
    const [showDropDown, setShowDropDown] = useState(false);

    const data = {
        commentId: comment.id,
        jwt: token
    }

    const handleLikeComment = () => {
        setIsCommentLiked(true)
        dispatch(likeCommentAction(data))
    }
    const handleUnlikeComment = () => {
        setIsCommentLiked(false)
        dispatch(unlikeCommentAction(data))
    }

    const handleClick = () => {
        setShowDropDown(!showDropDown);
    }

    const handleDeleteComment = () => {
        dispatch(deleteCommentAction(data))
    }

    useEffect(() => {

        setIsCommentLiked(isCommentLikedByUser(comment, user.reqUser.id))
        
    }, [comment, user.reqUser])

    return (
        <div>
            <div className='flex items-center justify-between py-5'>
                <div className='flex items-center'>
                    <div>
                        <img
                            className='w-9 h-9 rounded-full'
                            src={comment.user?.userimage || "https://i.ibb.co/3h581Xp/Adobe-Stock-349497933.jpg"}  
                            alt=""/>
                    </div>
                    <div className='ml-3'>
                        <p>
                            <span className='font-semibold'>{comment?.user.username}</span>

                            <span className='ml-2'>{comment.content}</span>
                        </p>
                        <div className='flex items-center space-x-3 text-xs opacity-60 pt-2'>
                            <span>{timeDifference(comment?.createdAt)}</span>
                            {comment?.likedByUsers?.length>0 && <span>{comment?.likedByUsers?.length} likes</span>}
                        </div>

                    </div>
                </div>
                <div class="flex items-center">
                {isCommentLiked ? (
                    <AiFillHeart onClick={handleUnlikeComment} className='text-xs hover:opacity-50 cursor-pointer text-red-600'/>
                ) : (
                    <AiOutlineHeart onClick={handleLikeComment} className='text-xs hover:opacity-50 cursor-pointer'/>
                    
                )}
                
                <div className='dropdown ml-1'>
                    <BsThreeDots className='dots' onClick={handleClick}/>
                    <div className='dropdown-content' style={{position: "sticky"}}>
                            {showDropDown && <p onClick={handleDeleteComment} className='bg-black text-white rounded-md cursor-pointer' >Delete</p>}
                        </div>
                        </div>

                </div>
                </div>
        </div>
    )
}

export default CommentCard