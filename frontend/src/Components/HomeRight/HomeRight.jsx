import React from 'react'
import SuggestionCard from './SuggestionCard'
import {useDispatch, useSelector} from 'react-redux';
import {suggestions} from '../../Config/Logics';

const HomeRight = ({user}) => {
    const dispatch = useDispatch();

    const handleSuggestions = () => {
        dispatch(suggestions(user?.reqUser))
    }

    return (
        <div className=''>
            <div>
                <div className='flex justify-between items-center'>
                    <div className='flex items-center'>
                        <div>
                            <img
                                className='w-12 h-12 rounded-full'
                                src={user?.reqUser
                                    ?.image || "https://i.ibb.co/3h581Xp/Adobe-Stock-349497933.jpg"}
                                alt=""/>
                        </div>
                        <div className='ml-3'>
                            <p>{
                                    user?.reqUser
                                        ?.name
                                }</p>
                            <p className='opacity-70'>{user?.username}</p>
                        </div>
                    </div>
                    <div>
                        <p className='text-blue-700 font-semibold'>switch</p>
                    </div>

                </div>
                <div className='space-y-5 mt-10'>
                    {/* {user.popularUser?.map((item) => (<SuggestionCard user={item}/>))} */}
                    {/* {
                        handleSuggestions().map(
                            (item) => (<SuggestionCard key={user.id} user={user}/>)
                        )
                    } */}
                </div>
            </div>
        </div>
    )
}

export default HomeRight