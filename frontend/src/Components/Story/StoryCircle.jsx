import React from 'react'
import { useNavigate } from 'react-router-dom'

const StoryCircle = ({user}) => {

  const navigate = useNavigate()

  const handleNavigate = () => {
    navigate(`/story/${user.id}`)
  }

  return (
    <div onClick={handleNavigate} className='cursor-pointer flex flex-col items-center'>
        <img className='w-16 h-16 rounded-full' src={user?.image || "https://i.ibb.co/3h581Xp/Adobe-Stock-349497933.jpg"} alt="" />
        <p>{user.username}</p> 
    </div>
  )
}

export default StoryCircle