import React from 'react'
import './Auth.css'
import Signin from '../../Components/Rester/Signin';
import {useLocation} from 'react-router-dom';
import Signup from '../../Components/Rester/Signup';

export const Auth = () => {
    const location = useLocation();
    return (
        <div>
            <div className="flex items-start justify-center mt-20 mx-64">
                <div>
                    <div className="relative hidden lg:block">
                        <div className="h-[44.3rem] w-[28.5rem]">
                            <img
                                className="h-[auto] w-[auto]"
                                src="https://static.cdninstagram.com/images/instagram/xig/homepage/phones/home-phones-2x.png?__makehaste_cache_breaker=73SVAexZgBW
                                "
                                alt=""/>
                            <div
                                className='mobileWallPaper'
                                style={{
                                    height: '75.5%',
                                    width: '54.5%',
                                    position: 'absolute',
                                    top: '22px',
                                    left: '151px'
                                }}></div>
                        </div>
                    </div>
                </div>
                <div
                    style={{
                        height: '100%',
                        marginTop: '1%'
                    }}>

                    {
                        location.pathname === "/login"
                            ? <Signin/>
                            : <Signup/>
                    }

                </div>
            </div>
        </div>
    )
}

export default Auth;