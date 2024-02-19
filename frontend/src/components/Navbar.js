import React from 'react'

function Navbar() {
  return (
    <nav className='navbar'>
        <div className="nav-wrapper">
            <div className="logo-box">logo</div>
            <ul className='nav-links'>
                <li>
                    <a href="#">home</a>
                </li>
                <li>
                    <a href="#">home</a>
                </li>
                <li>
                    <a href="#">home</a>
                </li>
            </ul>
            <div className="auth-btns-box">
                <button className="auth-btn login">login</button>
                <button className="auth-btn register">register</button>
            </div>
        </div>
    </nav>
  )
}

export default Navbar