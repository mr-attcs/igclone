import {Box, Button, FormControl, FormErrorMessage, Input} from '@chakra-ui/react';
import {Field, Form, Formik} from 'formik';
import React, {useEffect} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {useNavigate} from 'react-router-dom';
import * as Yup from 'yup';
import {signInAction} from '../../Redux/Auth/Action';
import {getUserProfileAction} from '../../Redux/User/Action';
const validationSchema = Yup
    .object()
    .shape({
        email: Yup
            .string()
            .email("Invalid Email Adress")
            .required("Email is required"),
        password: Yup
            .string()
            .min(8, "Password must contain atleast 8 characters")
            .required("Password is required")
    })

export const Signin = () => {
    localStorage.setItem("testUsername", "testUsername");
    const initialValues = {email: "", password: ""};
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const { user } = useSelector((store) => store);
    const jwt = localStorage.getItem("token");

    const handleSubmit = (values, actions) => {
        dispatch(signInAction(values))
        actions.setSubmitting(false)
    }

    useEffect(() => {
        if (jwt) dispatch(getUserProfileAction(jwt));
    }, [dispatch, jwt]);

    useEffect(() => {
        if (user.reqUser?.username) {
            navigate(`/${user.reqUser?.username}`);
        }
    }, [navigate, jwt, user.reqUser]);

    const handleNavigate = () => navigate("/signup")

    return (
        <div>
            <div className='border'>
                <Box p={8} display={'flex'} flexDirection={'column'} alignItems={'center'}>
                    <img
                        style={{
                            height: '71px',
                            width: '57%',
                            marginBottom: '10%'
                        }}
                        src="https://i.ibb.co/Gt57PPb/5a4e432a2da5ad73df7efe7a.png"
                        alt=""/>

                    <Formik
                        initialValues={initialValues}
                        onSubmit={handleSubmit}
                        validationSchema={validationSchema}>
                        {
                            (formikProps) => <Form className='space-y-8 w-[310px]'>

                                    <Field name="email">
                                        {
                                            ({field, form}) => (
                                                <FormControl isInvalid={form.errors.email && form.touched.email}>

                                                    <Input
                                                        className='w-full'
                                                        {...field}
                                                        id='email'
                                                        placeholder='Phonenumber, Username or Email'></Input>
                                                    <FormErrorMessage>{form.errors.email}</FormErrorMessage>
                                                </FormControl>
                                            )
                                        }

                                    </Field>

                                    <Field name="password">
                                        {
                                            ({field, form}) => (
                                                <FormControl
                                                    isInvalid={form.errors.password && form.touched.password}
                                                    style={{
                                                        marginTop: '10px'
                                                    }}>

                                                    <Input className='w-full' {...field} id='password' placeholder='Password'></Input>
                                                    <FormErrorMessage>{form.errors.password}</FormErrorMessage>
                                                </FormControl>
                                            )
                                        }

                                    </Field>

                                    <Button
                                        className='w-full'
                                        colorScheme='blue'
                                        type='submit'
                                        isLoading={formikProps.isSubmitting}
                                        style={{
                                            marginTop: '10px'
                                        }}>
                                        Sign in
                                    </Button>
                                </Form>
                        }

                    </Formik>
                </Box>

            </div>
            <div className='border w-full border-slate-300 mt-5'>
                <p className='text-center py-2 mt-[10px] mb-[10px] text-sm'>Don't have an account?<span onClick={handleNavigate} className='ml-2 text-blue-700 cursor-pointer'>Sign up</span>
                </p>
            </div>
        </div>
    )
}

export default Signin