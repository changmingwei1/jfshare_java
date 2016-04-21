package com.jfshare.ridge.zookeeper;

import java.io.Serializable;


public final class URL implements Serializable {

    private static final long serialVersionUID = -1985165475234910535L;

	private String username;

	private String password;

	private String host;

	private int port;

	private String serverList;

	public URL(String serverList){
    	this.serverList = serverList;
    }


	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public String getAuthority() {
	    if ((username == null || username.length() == 0)
	            && (password == null || password.length() == 0)) {
	        return null;
	    }
	    return (username == null ? "" : username) 
	            + ":" + (password == null ? "" : password);
	}

	public String getHost() {
		return host;
	}
	
	
	public int getPort() {
		return port;
	}

    public int getPort(int defaultPort) {
        return port <= 0 ? defaultPort : port;
    }

	public String getAddress() {
	    return port <= 0 ? host : host + ":" + port;
	}
	
	public String getServerList() {
		return serverList;
	}
	

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((host == null) ? 0 : host.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + port;
        result = prime * result + ((serverList == null) ? 0 : serverList.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        URL other = (URL) obj;
        if (host == null) {
            if (other.host != null)
                return false;
        } else if (!host.equals(other.host))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (port != other.port)
            return false;
        if (serverList == null) {
            if (other.serverList != null)
                return false;
        } else if (!serverList.equals(other.serverList))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

}