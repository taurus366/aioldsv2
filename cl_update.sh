#!/bin/bash

# Define the function for cl_update command
cl_update() {
    # Check if all required arguments are provided
    if [ $# -ne 3 ]; then
        echo "Usage: cl_update <SSH_USER@REMOTE_HOST> <FILE_NAME> <REMOTE_DIRECTORY>"
        return 1
    fi

    # Extract arguments
    SSH_USER_REMOTE_HOST=$1
    FILE_NAME=$2
    REMOTE_DIRECTORY=$3

    # Build Vaadin Java project
    mvn clean package -Pproduction || { echo "Error: Maven build failed"; return 1; }

    # Check if the project JAR file exists
    PROJECT_JAR="MainModule/target/${FILE_NAME}"
    if [ ! -f "$PROJECT_JAR" ]; then
        echo "Error: JAR file not found for project $FILE_NAME"
        return 1
    fi

    # Debug message
    echo "Found JAR file: $PROJECT_JAR"

    # Copy the JAR file to the remote server
    scp "$PROJECT_JAR" "$SSH_USER_REMOTE_HOST:$REMOTE_DIRECTORY" || { echo "Error: SCP failed"; return 1; }

    # SSH login and perform necessary actions on the remote server
    ssh "$SSH_USER_REMOTE_HOST" << EOF
        # Extract filename from the full path
        FILE_NAME=\$(basename "$FILE_NAME")

        # Replace the JAR file
#        mv "\$REMOTE_DIRECTORY/"$FILE_NAME "\$REMOTE_DIRECTORY/\$FILE_NAME" || { echo "Error: Unable to replace the JAR file"; exit 1; }

        # Find the PID of the Java process corresponding to the project
        PID=\$(pgrep -f "\$FILE_NAME")

        # Restart the Java process if it is running
        if [ -n "\$PID" ]; then
            echo "Restarting process with PID \$PID..."
            kill -9 "\$PID"
        else
            echo "Process \$FILE_NAME is not running."
        fi

         # Start a new Java process
        nohup java -jar "$REMOTE_DIRECTORY/$FILE_NAME" > $REMOTE_DIRECTORY/console.log 2>&1 &
EOF

    echo "Update completed successfully"
}

# Parse command line arguments
if [ $# -ne 3 ]; then
    echo "Usage: cl_update <SSH_USER@REMOTE_HOST> <FILE_NAME> <REMOTE_DIRECTORY>"
    exit 1
fi

# Call the cl_update function with provided arguments
cl_update "$@"
