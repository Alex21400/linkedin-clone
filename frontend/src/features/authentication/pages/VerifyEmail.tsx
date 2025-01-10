import React, { FormEvent, useState } from 'react'
import Layout from '../components/Layout'
import Box from '../components/Box'
import Input from '../components/Input'
import Button from '../components/Button'
import { useNavigate } from 'react-router-dom'
import { useAuthContext } from '../context/AuthenticationContextProvider'

const VerifyEmail: React.FC = () => {
  const [verificationCode, setVerificationCode] = useState("");
  const [message, setMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  
  const { user, setUser } = useAuthContext();

  const navigate = useNavigate();

  const verifyEmail = async (verificationCode: string) => {
    setMessage("");

    try {
      const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/authentication/verify-email`, {
        method: "PUT",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ verificationCode })
      });
  
      if(response.ok) {
          setErrorMessage("");
          setUser({ ...user!, emailVerified: true });
          navigate('/');
      } else {
          const { message } = await response.json();
          setErrorMessage(message);
      }
    } catch(e) {
      console.log(e);
      setErrorMessage("Something went wrong, please try again");
    } finally {
      setIsLoading(false);
    }
  }

  const sendEmailVerificationCode = async () => {
    setErrorMessage("");
    setVerificationCode("");

    try {
      const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/authentication/send-email-verification-code`, { 
        headers: {
          Authorization: `Bearer ${localStorage.getItem("accessToken")}`
      },
      });
  
      if(response.ok) {
          setErrorMessage("");
          setMessage("Code sent successfully. Please check your email.");
      } else {
          const { message } = await response.json();
          setErrorMessage(message);
      }
    } catch(e) {
      console.log(e);
      setErrorMessage("Something went wrong, please try again");
    } finally {
      setIsLoading(false);
    }
  }

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);
    await verifyEmail(verificationCode);
    setIsLoading(false);
  }

  return (
    <Layout>
        <Box>
          <h2 className='text-3xl font-semibold mb-4'>Verify Email</h2>
          <form onSubmit={handleSubmit}>
            <p>Only one step left to complete your registration. Please enter the code we have sent to Your email.</p>
            <Input 
              label='Verification Code'
              type='text'
              name='verificationCode'
              key='verificationCode' 
              value={verificationCode}
              onChange={(e) => setVerificationCode(e.target.value)}
              />
            {message && <p className='text-green-500 mb-4'>{message}</p>}
            {errorMessage && <p className='text-red-500 mb-4'>{errorMessage}</p>}
            <Button 
              type='submit'
              disabled={isLoading}
            >
              Verify Email
            </Button>
            <Button
              outline
              type='button'
              disabled={isLoading}
              onClick={() => {
                sendEmailVerificationCode();
              }}
            >
              Send again
            </Button>
          </form>
        </Box>
    </Layout>
  )
}

export default VerifyEmail