import { Box, Button, FormControl, FormErrorMessage, Input, useToast } from '@chakra-ui/react'
import { Form, Field, Formik } from 'formik'
import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import * as Yup from 'yup'
import { signUpAction } from '../../Redux/Auth/Action'

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

const Signup = () => {

    const initialValues={email:"", username:"", name:"", password:""}
    const navigate = useNavigate();
    const toast = useToast();
    const dispatch = useDispatch();
    const { auth } = useSelector((store) => store);

    const handleNavigate = () => navigate("/login");

    const handleSubmit = (values, actions) => {
        console.log("values: ", values);
        dispatch(signUpAction(values))
        actions.setSubmitting(false);
    };

    useEffect(() => {
        if(auth.signup?.username){
            navigate('/login')
            toast({
                title: `Account created. ${auth.signup?.username}`,
                status: 'success',
                duration: 5000,
                isClosable: true,
              })
        }
        

    },[auth.signup]) 
    
  return (
    <div>
        <div className='border'>
                <Box p={8} display={'flex'} flexDirection={'column'} alignItems={'center'} >
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
                                                <FormControl isInvalid={form.errors.email && form.touched.email} >

                                                    <Input
                                                        className='w-full'
                                                        {...field}
                                                        id='email'
                                                        placeholder='Mobile Number or Email'></Input>
                                                    <FormErrorMessage>{form.errors.email}</FormErrorMessage>
                                                </FormControl>
                                            )
                                        }

                                    </Field>

                                    <Field name="name">
                                        {
                                            ({field, form}) => (
                                                <FormControl isInvalid={form.errors.name && form.touched.name} style={{marginTop: '10px'}} >

                                                    <Input
                                                        className='w-full'
                                                        {...field}
                                                        id='name'
                                                        placeholder='Full Name'></Input>
                                                    <FormErrorMessage>{form.errors.name}</FormErrorMessage>
                                                </FormControl>
                                            )
                                        }

                                    </Field>

                                    <Field name="username">
                                        {
                                            ({field, form}) => (
                                                <FormControl isInvalid={form.errors.username && form.touched.username} style={{marginTop: '10px'}} >

                                                    <Input
                                                        className='w-full'
                                                        {...field}
                                                        id='username'
                                                        placeholder='Username'></Input>
                                                    <FormErrorMessage>{form.errors.username}</FormErrorMessage>
                                                </FormControl>
                                            )
                                        }

                                    </Field>

                                    

                                    <Field name="password">
                                        {
                                            ({field, form}) => (
                                                <FormControl isInvalid={form.errors.password && form.touched.password} style={{marginTop: '10px'}}>

                                                    <Input className='w-full' {...field} id='password' placeholder='Password'></Input>
                                                    <FormErrorMessage>{form.errors.password}</FormErrorMessage>
                                                </FormControl>
                                            )
                                        }

                                    </Field>

                                    <p className='text-center text-xs'>People who use our service may have uploaded your contact information to Instagram. Learn More</p>
                                    <p className='text-center text-xs'>By signing up, you agree to our Terms. Learn how we collect, use and share your data in our Privacy Policy and how we use cookies and similar technology in our Cookies Policy.</p>    
                                    <Button
                                        className='w-full'
                                        colorScheme='blue'
                                        type='submit'
                                        isLoading={formikProps.isSubmitting}
                                        style={{marginTop: '10px'}}>
                                        Sign up
                                    </Button>
                                </Form>
                        }

                    </Formik>
                </Box>

            </div>
            <div className='border w-full border-slate-300 mt-5' >
                <p className='text-center py-2 mt-[10px] mb-[10px] text-sm'>Have an account?<span onClick={handleNavigate} className='ml-2 text-blue-700 cursor-pointer'>Log in</span></p>
            </div>
    </div>
  )
}

export default Signup