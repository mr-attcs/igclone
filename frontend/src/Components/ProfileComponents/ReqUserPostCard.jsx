import React, { useEffect, useState } from 'react'
import { AiFillHeart } from 'react-icons/ai'
import {FaComment} from 'react-icons/fa'
import "./ReqUserPostCard.css"
import { useNavigate } from 'react-router-dom'
import { useDisclosure } from '@chakra-ui/react'
import { useDispatch, useSelector } from 'react-redux'
import { likePostAction, savePostAction } from '../../Redux/Post/Action'
import CommentModal from '../Comment/CommentModal'
import { isPostLikedByUser, isSavedPost } from '../../Config/Logics'

const ReqUserPostCard = ({post}) => {

    const [isPostLiked, setIsPostLiked] = useState(false);
    const [isSaved, setIsSaved] = useState(false);
    const {isOpen, onOpen, onClose} = useDisclosure();
    const {user} = useSelector(store => store);
    const dispatch = useDispatch();
    const token = localStorage.getItem("token");
    const navigate = useNavigate();

    const data = {
        jwt: token,
        postId: post?.id
    }

    const handleSavePost = () => {
        setIsSaved(true)
        dispatch(savePostAction(data))
    }

    const handlePostLike = () => {
        setIsPostLiked(true)
        dispatch(likePostAction(data))
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
        <div className='p-2' onClick={handleOpenCommentModal}>
            <div className='post w-60 h-60 cursor-pointer'>
                <img className='cursor-pointer' src= {post?.image} alt=""/>
                <div className='overlay'>
                    <div className='overlay-text flex justify-between '>
                        <div>
                            <AiFillHeart></AiFillHeart><span>{post?.likedByUsers?.length}</span>
                        </div>
                        <div><FaComment/><span>{post?.comments.length}</span></div>
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

export default ReqUserPostCard