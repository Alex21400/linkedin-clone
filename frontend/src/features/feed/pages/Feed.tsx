import React from 'react';
import { useAuthContext } from '../../authentication/context/AuthenticationContextProvider'

const Feed: React.FC = () => {
  const { user, signOut } = useAuthContext();

  return (
    <div className='min-h-screen grid grid-rows-[auto_1fr]'>
      <header className='h-16 flex justify-center items-center gap-4 p-4'>
        <p>Hello {user?.email}</p>
        |
        <button onClick={() => signOut()}>Sign Out</button>
      </header>
      <main className='grid grid-rows-[10rem_1fr_10rem] xl:grid-cols-[15rem_1fr_18rem] xl:grid-rows-[auto] gap-8 px-8 max-w-[74rem] mx-auto w-full'>
        {/* LEFT */}
        <div className='bg-secondary h-[30rem]'></div>
        {/* CENTER */}
        <div className='bg-gray-500 h-full'>
          <div style={{ fontSize: "1rem" }}>
            This text is 1rem (Base font-size).
          </div>
        </div>
        {/* RIGHT */}
        <div className='bg-secondary h-80'></div>
      </main>
    </div>
  )
}

export default Feed