import React, { ChangeEvent, FormEvent, useState } from 'react'
import Layout from '../components/Layout'
import Box from '../components/Box'
import Input from '../components/Input'
import Button from '../components/Button'
import { Link, useNavigate } from 'react-router-dom'
import Separator from '../components/Separator'
import { useAuthContext } from '../context/AuthenticationContextProvider'

interface IFormData {
    email: string;
    password: string;
}

const Signup: React.FC = () => {
  const [errorMessage, setErrorMessage] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [formData, setFormData] = useState<IFormData>({
    email: '',
    password: ''
  });

  const { signUp } = useAuthContext();

  const navigate = useNavigate();

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  }

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);

    const { email, password } = formData;

    try {
        await signUp(email, password);
        navigate('/');
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
    <Layout onSignUpPage>
        <h2 className='text-3xl text-center p-4 -mt-52'>
            Join LinkedIn now — it’s free!
            </h2>                    
        <Box signUp>
            <form onSubmit={(e) => handleSubmit(e)}>
                <Input
                    label='Email' 
                    id='email' 
                    type='email'
                    name='email'
                    onChange={(e) => handleChange(e)}
                />
                <Input 
                    label='Password' 
                    id='password' 
                    type='password'
                    name='password'
                    onChange={(e) => handleChange(e)}
                />
                {errorMessage && <p className='text-red-500 mb-4'>{errorMessage}</p>}
                <p className='text-xs text-center'>
                    By clicking Agree & Join or Continue, you agree to LinkedIn's{" "}
                    <Link className='text-primary font-semibold' to={""}>IUser Agreement</Link>, <Link className='text-primary font-semibold' to={""}>Privacy Policy</Link>, and{" "}
                    <Link className='text-primary font-semibold' to={""}>Cookie Policy</Link>.
            </p>
                <Button 
                    type='submit'
                    disabled={isLoading}
                >
                    Agree & Join
                </Button>
            </form>
            <Separator>Or</Separator>
            <div className='text-center text-sm p-4'>
                Already on LinkedIn? <Link to={"/authentication/sign-in"} className='text-primary font-semibold'>Sign in</Link>
            </div>
        </Box>
    </Layout>
  )
}

export default Signup