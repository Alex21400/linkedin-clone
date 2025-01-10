import React, { ChangeEvent, FormEvent, useState } from 'react'
import Layout from '../components/Layout'
import Box from '../components/Box'
import Button from '../components/Button'
import { Link, useLocation, useNavigate } from 'react-router-dom'
import Separator from '../components/Separator'
import { useAuthContext } from '../context/AuthenticationContextProvider'
import SignInInput from '../components/SignInInput'

interface IFormData {
  email: string;
  password: string;
}

const SignIn: React.FC = () => {
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [formData, setFormData] = useState<IFormData>({
    email: '',
    password: ''
  });

  const { signIn } = useAuthContext();

  const navigate = useNavigate();
  const location = useLocation();

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  }

  const handleSignIn = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);

    const { email, password } = formData;

    try {
      await signIn(email, password);
      const destination = location.state?.from || '/';
      navigate(destination);
    } catch(error) {
      if(error instanceof Error) {
        setErrorMessage(error.message);
      } else {
        setErrorMessage("An unkown error has occured");
      }
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <Layout>
        <Box>
            <h2 className='text-3xl font-semibold mb-2'>Sign in</h2>
            <p className='text-sm text-darkGray mb-6'>Stay updated on your professional world.</p>
            <form onSubmit={handleSignIn}>
                <SignInInput 
                  label='Email' 
                  id='email' 
                  type='email'
                  name='email'
                  value={formData.email}
                  onChange={(e) => handleChange(e)} 
                  onFocus={() => setErrorMessage("")}
                />
                <SignInInput 
                  label='Password' 
                  id='password' 
                  type='password'
                  name='password'
                  value={formData.password}
                  onChange={(e) => handleChange(e)}
                />
                {errorMessage && <p className='text-red-500 mb-4'>{errorMessage}</p>}
                <Link className='text-primary font-semibold' to={"/authentication/request-password-reset"}>Forgot password?</Link>
                <Button type='submit' disabled={isLoading}>
                  Sign in
                </Button>
            </form>
            <Separator>Or</Separator>
        </Box>
        <div className='text-center text-sm mt-8'>
                <p>
                  New to LinkedIn? &nbsp;
                  <Link to={"/authentication/sign-up"} className='text-primary font-medium'>Join now</Link>
                </p>
            </div>
    </Layout>
  )
}

export default SignIn